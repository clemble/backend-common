package com.clemble.casino.server.spring.common;

public interface SpringConfiguration {

    String TEST = "test"; // unit tests
    String DEFAULT = "default"; // localhost:8080
    String CLOUD = "cloud"; // amazon currently

    String INTEGRATION_TEST = "int_test";
    // TODO remove INTEGRATION_CLOUD & INTEGRATION_DEFAULT
    String INTEGRATION_CLOUD = "int_cloud";
    String INTEGRATION_DEFAULT = "int_default"; // localhost:8080

}
