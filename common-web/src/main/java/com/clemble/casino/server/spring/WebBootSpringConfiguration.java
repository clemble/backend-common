package com.clemble.casino.server.spring;

import com.clemble.casino.server.spring.common.SpringConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by mavarazy on 3/30/15.
 */
@Import({
    ServerPropertiesAutoConfiguration.class,
    DispatcherServletAutoConfiguration.class,
    EmbeddedServletContainerAutoConfiguration.class,
    WebCommonSpringConfiguration.class
})
public class WebBootSpringConfiguration implements SpringConfiguration {
}
