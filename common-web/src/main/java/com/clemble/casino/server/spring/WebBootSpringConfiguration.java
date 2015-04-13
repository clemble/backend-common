package com.clemble.casino.server.spring;

import com.clemble.casino.server.spring.common.SpringConfiguration;
import com.google.common.collect.ImmutableMap;
import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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

    @Bean
    public CORSFilterInitializer filterInitializer(){
        return new CORSFilterInitializer();
    }

    public static class CORSFilterInitializer implements ServletContextInitializer {
        @Override
        final public void onStartup(ServletContext container) throws ServletException {
           // Step 1. Creating CORS filter
           FilterRegistration.Dynamic filter = container.addFilter("CORS", CORSFilter.class);
           if (filter != null) {
               filter.setInitParameters(ImmutableMap.of(
                       "cors.allowOrigin", "*",
                       "cors.supportedHeaders", "Accept, Origin, Content-Type, playerId, sessionId, tableId",
                       "cors.supportedMethods", "GET, POST, HEAD, OPTIONS, DELETE"
               ));
               filter.addMappingForUrlPatterns(null, false, "/*");
           }
       }
    }

}
