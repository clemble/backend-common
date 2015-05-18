package com.clemble.casino.server.error;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.clemble.casino.error.ClembleException;

public class ClembleConstraintExceptionResolver implements PersistenceExceptionTranslator {

    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return check(ex);
    }

    private DataAccessException check(Throwable ex) {
        if (ex == null)
            return null;
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = (ConstraintViolationException) ex;
            ClembleException casinoException = ClembleException.fromGenericConstraintViolations(violationException.getConstraintViolations());
            casinoException.setStackTrace(ex.getStackTrace());
            return new ClembleDataAccessException(casinoException);
        } else {
            return check(ex.getCause());
        }
    }

}
