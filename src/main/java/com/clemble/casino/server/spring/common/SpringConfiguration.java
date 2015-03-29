package com.clemble.casino.server.spring.common;

public interface SpringConfiguration {

    final public static String TEST = "test"; // unit tests
    final public static String DEFAULT = "default"; // localhost:8080
    final public static String CLOUD = "cloud"; // amazon currently

    final public static String INTEGRATION_TEST = "int_test";
    // TODO remove INTEGRATION_CLOUD & INTEGRATION_DEFAULT
    final public static String INTEGRATION_CLOUD = "int_cloud";
    final public static String INTEGRATION_DEFAULT = "int_default"; // localhost:8080

}
