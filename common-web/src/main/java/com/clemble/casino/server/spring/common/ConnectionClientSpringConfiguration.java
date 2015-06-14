package com.clemble.casino.server.spring.common;

import com.clemble.casino.player.PlayerConnection;
import com.clemble.casino.player.service.PlayerConnectionService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by mavarazy on 11/9/14.
 */
@Configuration
@Import({ ConnectionClientSpringConfiguration.Test.class, ConnectionClientSpringConfiguration.Default.class })
public class ConnectionClientSpringConfiguration implements SpringConfiguration{

    @Configuration
    @Profile(value = { TEST })
    public static class Test {

        @Autowired(required = false)
        @Qualifier("playerConnectionController")
        public PlayerConnectionService playerConnectionController;

        @Bean
        public PlayerConnectionService playerConnectionClient(){
            if (playerConnectionController != null)
                return playerConnectionController;
            return new PlayerConnectionService() {
                @Override
                public Set<PlayerConnection> myConnections() {
                    return Collections.emptySet();
                }
                @Override
                public Integer myConnectionsCount() {
                    return 0;
                }
                @Override
                public Set<PlayerConnection> getConnections(String player) {
                    return Collections.emptySet();
                }
                @Override
                public Integer getConnectionsCount(String player) {
                    return 0;
                }
            };
        }
    }

    @Configuration
    @Profile(value = { DEFAULT, INTEGRATION_TEST, INTEGRATION_DEFAULT, CLOUD })
    public static class Default {

        final public static String CONNECTION_EXCHANGE = "connection";
        final public static String CONNECTION_ROUTING_KEY = "connection_client";
        final public static String CONNECTION_QUEUE = "connection_queue";

        @Autowired(required = false)
        @Qualifier("playerConnectionController")
        public PlayerConnectionService playerConnectionController;

        @Bean
        public PlayerConnectionService playerConnectionClient(
            @Value("${clemble.service.notification.system.user}") String user,
            @Value("${clemble.service.notification.system.password}") String password,
            @Value("${SYSTEM_NOTIFICATION_SERVICE_HOST}") String host
        ) throws Exception {
            if (playerConnectionController != null)
                return playerConnectionController;

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setUsername(user);
            connectionFactory.setPassword(password);
            connectionFactory.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("CL account client %d").build());

            CachingConnectionFactory springConnectionFactory = new CachingConnectionFactory(connectionFactory);

            RabbitTemplate rabbitTemplate = new RabbitTemplate();
            rabbitTemplate.setExchange(CONNECTION_EXCHANGE);
            rabbitTemplate.setRoutingKey(CONNECTION_ROUTING_KEY);
            rabbitTemplate.setConnectionFactory(springConnectionFactory);

            RabbitAdmin rabbitAdmin = new RabbitAdmin(springConnectionFactory);
            rabbitAdmin.declareExchange(new DirectExchange(CONNECTION_EXCHANGE, true, false));
            rabbitAdmin.declareQueue(new Queue(CONNECTION_QUEUE, true));
            rabbitAdmin.declareBinding(new Binding(CONNECTION_QUEUE, Binding.DestinationType.QUEUE, CONNECTION_EXCHANGE, CONNECTION_ROUTING_KEY, new HashMap<String, Object>()));

            AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
            factoryBean.setAmqpTemplate(rabbitTemplate);
            factoryBean.setServiceInterface(PlayerConnectionService.class);
            factoryBean.afterPropertiesSet();
            return (PlayerConnectionService) factoryBean.getObject();
        }
    }

}
