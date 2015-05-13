package com.clemble.casino.server.error;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clemble.casino.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clemble.casino.client.error.ClembleCasinoResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClembleCasinoHandlerExceptionResolver implements HandlerExceptionResolver {
    
    final private Logger LOG = LoggerFactory.getLogger(ClembleCasinoHandlerExceptionResolver.class);

    final private ObjectMapper objectMapper;

    public ClembleCasinoHandlerExceptionResolver(ObjectMapper objectMapper) {
        this.objectMapper = checkNotNull(objectMapper);
    }

    @Override
    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(ex instanceof ClembleCasinoException)) {
            LOG.error("while processing {} with {}", request, handler);
            LOG.error("Log trace ", ex);
        }
        ClembleCasinoFailureDescription clembleFailure = null;
        if (ex instanceof MethodArgumentNotValidException) {
            Set<ClembleCasinoError> serverErrors = new HashSet<>();
            Set<ClembleCasinoFieldError> fieldErrors = new HashSet<>();

            for(ObjectError error:  ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()) {
                ClembleCasinoError clembleCasinoError = ClembleCasinoError.forCode(error.getDefaultMessage());
                if (error instanceof FieldError) {
                    String field = ((FieldError) error).getField();
                    fieldErrors.add(new ClembleCasinoFieldError(field, clembleCasinoError));
                } else {
                    serverErrors.add(clembleCasinoError);
                }
            }

            clembleFailure = new ClembleCasinoFailureDescription(fieldErrors, serverErrors);
        } else if (ex instanceof ClembleCasinoException) {
            clembleFailure = ((ClembleCasinoException) ex).getFailureDescription();
        } else if(ex instanceof ClembleCasinoServerException) {
            clembleFailure = ((ClembleCasinoServerException) ex).getCasinoException().getFailureDescription();
        } else if (ex instanceof ServletRequestBindingException) {
            clembleFailure = ClembleCasinoFailureDescription.SERVER_ERROR;
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setHeader("Content-Type", "application/json");
        for(ClembleCasinoError failure: clembleFailure.getServer()) {
            response.setHeader(ClembleCasinoResponseErrorHandler.ERROR_CODES_HEADER, failure.getCode());
        }

        try {
            objectMapper.writeValue(response.getOutputStream(), clembleFailure);
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }

        return new ModelAndView();
    }
}
