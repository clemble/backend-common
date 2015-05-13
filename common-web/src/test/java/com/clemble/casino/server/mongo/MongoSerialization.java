package com.clemble.casino.server.mongo;

import com.clemble.casino.server.spring.common.MongoSpringConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mavarazy on 5/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoSpringConfiguration.class })
public class MongoSerialization {

    @Autowired
    public MongoTemplate mongoTemplate;

    @Test
    public void testDateTimeZoneSerialization() {
        DBObject mongoPresentation = new BasicDBObject();
        mongoTemplate.getConverter().write(DateTimeZone.UTC, mongoPresentation);
        Assert.assertNotNull(mongoPresentation);

        DateTimeZone zone = mongoTemplate.getConverter().read(DateTimeZone.class, mongoPresentation);
        Assert.assertEquals(DateTimeZone.UTC, zone);
    }

//    @Test
//    public void testRead() {
//        DBObject mongoPresentation = new BasicDBObject();
//        mongoPresentation.put("tz", "UTC");
//
//        MongoConverter converter = mongoTemplate.getConverter();
//        DateTimeZone zone = converter.read(DateTimeZone.class, mongoPresentation);
//        Assert.assertEquals(DateTimeZone.UTC, zone);
//    }

}
