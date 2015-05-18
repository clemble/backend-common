package com.clemble.casino.server.spring.common;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.*;

import com.clemble.casino.error.ClembleValidationService;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@Import({
    PropertiesSpringConfiguration.class,
    RabbitSpringConfiguration.class,
    SystemNotificationSpringConfiguration.class})
public class CommonSpringConfiguration implements SpringConfiguration {

    @Bean
    public ClembleValidationService clembleValidationService(ValidatorFactory validatorFactory) {
        return new ClembleValidationService(validatorFactory);
    }

    @Bean
    public ValidatorFactory validatorFactory(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return Validation.buildDefaultValidatorFactory();
    }

    @Bean
    public Validator springValidator(ValidatorFactory validatorFactory) {
        CustomValidatorBean factoryBean = new CustomValidatorBean();
        factoryBean.setValidatorFactory(validatorFactory);
        return factoryBean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(ValidatorFactory validatorFactory) {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidatorFactory(validatorFactory);
        methodValidationPostProcessor.setProxyTargetClass(true);
        return methodValidationPostProcessor;
    }

}
