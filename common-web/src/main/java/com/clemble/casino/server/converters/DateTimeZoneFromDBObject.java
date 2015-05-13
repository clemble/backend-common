package com.clemble.casino.server.converters;

import com.mongodb.DBObject;
import org.joda.time.DateTimeZone;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Created by mavarazy on 5/12/15.
 */
@ReadingConverter
public class DateTimeZoneFromDBObject implements Converter<DBObject, DateTimeZone> {

    @Override
    public DateTimeZone convert(DBObject source) {
        return DateTimeZone.forID((String) source.get("tz"));
    }

}

