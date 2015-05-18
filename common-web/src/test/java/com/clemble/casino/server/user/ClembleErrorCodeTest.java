package com.clemble.casino.server.user;

import org.junit.Test;

import com.clemble.casino.error.ClembleErrorCode;
import com.clemble.casino.error.ClembleErrorCode.Code;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class ClembleErrorCodeTest {

    @Test
    public void testCodeValid() throws IllegalAccessException {
        Class<?> codeClass = ClembleErrorCode.Code.class;
        Collection<Field> staticFields = new ArrayList<Field>();
        for(Field field: codeClass.getFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                staticFields.add(field);
                String fieldValue = field.get(new Code()).toString();
                if (ClembleErrorCode.valueOf(fieldValue) == null)
                    throw new IllegalArgumentException();
            }
        }


    }

}
