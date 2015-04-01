package com.clemble.casino.server.spring.common;

import com.clemble.casino.bet.json.BetJsonModule;
import com.clemble.casino.goal.json.GoalJsonModule;
import com.clemble.casino.json.CommonJsonModule;
import com.clemble.casino.json.GenericJsonModule;
import com.clemble.casino.payment.json.PaymentJsonModule;
import com.clemble.casino.player.json.PlayerJsonModule;
import com.clemble.casino.tag.json.TagJsonModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.clemble.casino.json.ObjectMapperUtils;

@Configuration
public class JsonSpringConfiguration implements SpringConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperUtils.OBJECT_MAPPER;
    }

    @Bean
    public JodaModule jodaModule() {
        return new JodaModule();
    }

    @Bean
    public GenericJsonModule genericJsonModule() {
        return new GenericJsonModule();
    }

    @Bean
    public CommonJsonModule commonJsonModule() {
        return new CommonJsonModule();
    }

    @Bean
    public PlayerJsonModule playerJsonModule() {
        return new PlayerJsonModule();
    }

    @Bean
    public BetJsonModule betJsonModule() {
        return new BetJsonModule();
    }

    @Bean
    public GoalJsonModule goalJsonModule() {
        return new GoalJsonModule();
    }

    @Bean
    public PaymentJsonModule paymentJsonModule() {
        return new PaymentJsonModule();
    }

    @Bean
    public TagJsonModule tagJsonModule() {
        return new TagJsonModule();
    }

}
