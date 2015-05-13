package com.clemble.casino.server.converters;

import org.bson.Transformer;
import org.joda.time.DateTimeZone;

/**
 * Created by mavarazy on 5/12/15.
 */
public class DateTimeZoneBSONTransformer implements Transformer{

    @Override
    public Object transform(Object o) {
        if (o instanceof DateTimeZone)
            return ((DateTimeZone) o).getID();
        else
            return DateTimeZone.forID(o.toString());
    }

}
