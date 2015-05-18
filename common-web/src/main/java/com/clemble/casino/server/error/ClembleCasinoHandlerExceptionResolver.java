package com.clemble.casino.server.error;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clemble.casino.client.error.ClembleResponseErrorHandler;
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
        if (!(ex instanceof ClembleException) && !(ex instanceof MethodArgumentNotValidException)) {
            LOG.error("while processing {} with {}", request, handler);
            LOG.error("Log trace ", ex);
        }
        ClembleError clembleFailure = null;
        if (ex instanceof MethodArgumentNotValidException) {
            Set<ClembleErrorCode> serverErrors = new HashSet<>();
            Set<ClembleFieldError> fieldErrors = new HashSet<>();

            for(ObjectError error:  ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()) {
                ClembleErrorCode clembleErrorCode = ClembleErrorCode.valueOf(error.getDefaultMessage());
                if (error instanceof FieldError) {
                    String field = ((FieldError) error).getField();
                    fieldErrors.add(new ClembleFieldError(field, clembleErrorCode));
                } else {
                    serverErrors.add(clembleErrorCode);
                }
            }

            clembleFailure = new ClembleError(fieldErrors, serverErrors);
        } else if (ex instanceof ClembleException) {
            clembleFailure = ((ClembleException) ex).getFailureDescription();
        } else if(ex instanceof ClembleServerException) {
            clembleFailure = ((ClembleServerException) ex).getCasinoException().getFailureDescription();
        } else {
            clembleFailure = ClembleError.SERVER_ERROR;
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setHeader("Content-Type", "application/json");
        for(ClembleErrorCode failure: clembleFailure.getServer()) {
            response.setHeader(ClembleResponseErrorHandler.ERROR_CODES_HEADER, failure.name());
        }

        try {
            objectMapper.writeValue(response.getOutputStream(), clembleFailure);
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }

        return new ModelAndView();
    }
}
