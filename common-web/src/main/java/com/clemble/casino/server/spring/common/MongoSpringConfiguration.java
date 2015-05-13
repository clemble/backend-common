package com.clemble.casino.server.spring.common;

import com.clemble.casino.server.converters.*;
import com.mongodb.MongoClient;
import com.sun.javafx.scene.layout.region.Margins;
import org.bson.BSON;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mavarazy on 8/26/14.
 */
@EnableMongoAuditing
@Import(PropertiesSpringConfiguration.class)
public class MongoSpringConfiguration implements SpringConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(
        @Value("${MONGO_SERVICE_SERVICE_HOST}") String host,
        @Value("${MONGO_SERVICE_SERVICE_PORT}") int port,
        @Value("${clemble.db.mongo.name}") String name) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoTemplate template = new MongoTemplate(mongoClient, name);

        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new StringToBreachPunishment());
        converters.add(new BreachPunishmentToString());

        converters.add(new StringToDateTimeZone());
        converters.add(new DateTimeZoneToString());
        //converters.add(new FixedDateTimeZoneToString());
        //converters.add(new DateTimeZoneToDBObject());
        //converters.add(new DateTimeZoneFromDBObject());

        CustomConversions customConversions = new CustomConversions(converters);
        MappingMongoConverter mongoConverter = (MappingMongoConverter) template.getConverter();
        mongoConverter.setCustomConversions(customConversions);

        GenericConversionService conversionService = (GenericConversionService) mongoConverter.getConversionService();
        for(Converter<?, ?> converter: converters)
            conversionService.addConverter(converter);

        return template;
    }

    @Bean
    public MongoRepositoryFactory mongoRepositoryFactory(MongoTemplate mongoOperations) throws UnknownHostException {
        return new MongoRepositoryFactory(mongoOperations);
    }

}
