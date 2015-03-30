package com.clemble.casino.server.spring.common;

import com.clemble.casino.server.converters.GameConfigurationConverter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.clemble.casino.server.error.ClembleConstraintExceptionResolver;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.clemble.casino.server.repository", includeFilters = { @ComponentScan.Filter(value = GraphRepository.class, type = FilterType.ASSIGNABLE_TYPE) })
abstract public class BasicNeo4JSpringConfiguration extends Neo4jConfiguration implements SpringConfiguration {

    abstract public String getFolder();

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService(
        @Value("${NEO4j_MASTER_SERVICE_HOST}") String host,
        @Value("${NEO4j_MASTER_SERVICE_PORT}") int port) {
        String url = "http://" + host + ":" + port + "/db/data";
        SpringRestGraphDatabase graphDatabase = new SpringRestGraphDatabase(url);
        return graphDatabase;
    }

    @Bean
    @Override
    public PersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new ClembleConstraintExceptionResolver();
    }

    @Override
    @Bean
    protected ConversionService neo4jConversionService() throws Exception {
        GenericConversionService conversionService = (GenericConversionService) super.neo4jConversionService();
        conversionService.addConverter(new GameConfigurationConverter());
        return conversionService;
    }

}
