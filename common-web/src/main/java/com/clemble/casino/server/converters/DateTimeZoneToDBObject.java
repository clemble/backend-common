package com.clemble.casino.server.converters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.joda.time.DateTimeZone;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Created by mavarazy on 5/12/15.
 */
@WritingConverter
public class DateTimeZoneToDBObject implements Converter<DateTimeZone, DBObject> {

    @Override
    public DBObject convert(DateTimeZone source) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("tz", source.getID());
        return dbObject;
    }

}
