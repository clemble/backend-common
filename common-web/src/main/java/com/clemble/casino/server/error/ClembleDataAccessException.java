package com.clemble.casino.server.error;

import com.clemble.casino.error.ClembleServerException;
import org.springframework.dao.DataAccessException;

import com.clemble.casino.error.ClembleException;

public class ClembleDataAccessException extends DataAccessException implements ClembleServerException {

    /**
     * Generated 13/12/13
     */
    private static final long serialVersionUID = -6807686742129584430L;

    final private ClembleException casinoException;

    public ClembleDataAccessException(ClembleException casinoException) {
        super(casinoException.getMessage(), casinoException.getCause());
        this.casinoException = casinoException;
    }

    @Override
    public ClembleException getCasinoException() {
        return casinoException;
    }

}
