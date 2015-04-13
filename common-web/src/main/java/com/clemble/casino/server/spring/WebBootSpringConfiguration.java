package com.clemble.casino.server.spring;

import com.clemble.casino.server.spring.common.SpringConfiguration;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public CORSFilter corsFilter() {
        CORSFilter corsFilter = new CORSFilter();
        return corsFilter;
    }

    public static class SimpleCORSFilter implements Filter {

        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "false");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, Content-Type, playerId, sessionId, tableId");
            chain.doFilter(req, res);
        }

        public void init(FilterConfig filterConfig) {
        }

        public void destroy() {
        }
    }

}
