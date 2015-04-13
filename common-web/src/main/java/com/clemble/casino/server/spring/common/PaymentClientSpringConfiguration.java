package com.clemble.casino.server.spring.common;

import com.clemble.casino.payment.PlayerAccount;
import com.clemble.casino.money.Currency;
import com.clemble.casino.payment.service.PlayerAccountService;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * Created by mavarazy on 7/5/14.
 */
@Configuration
@Import({ SystemNotificationSpringConfiguration.class, PaymentClientSpringConfiguration.Test.class, PaymentClientSpringConfiguration.Default.class })
public class PaymentClientSpringConfiguration implements SpringConfiguration {

    @Configuration
    @Profile(value = { TEST })
    public static class Test {

        @Autowired(required = false)
        @Qualifier("playerAccountController")
        public PlayerAccountService playerAccountController;

        @Bean
        public PlayerAccountService playerAccountClient(){
            if (playerAccountController != null)
                return playerAccountController;
            return new PlayerAccountService() {
                @Override
                public PlayerAccount myAccount() {
                    return null;
                }
                @Override
                public PlayerAccount getAccount(String playerWalletId) {
                    return null;
                }
                @Override
                public List<String> canAfford(Collection<String> players, Currency currency, Long amount) {
                    return Collections.emptyList();
                }
            };
        }
    }

    @Configuration
    @Profile(value = { DEFAULT, INTEGRATION_TEST, INTEGRATION_DEFAULT, CLOUD })
    public static class Default {

        final public static String PAYMENT_EXCHANGE = "payment";
        final public static String PAYMENT_ROUTING_KEY = "payment_client";
        final public static String PAYMENT_QUEUE = "payment_queue";

        @Autowired(required = false)
        @Qualifier("playerAccountController")
        public PlayerAccountService playerAccountController;

        @Bean
        public PlayerAccountService playerAccountClient(
            @Value("${clemble.service.notification.system.user}") String user,
            @Value("${clemble.service.notification.system.password}") String password,
            @Value("${SYSTEM_NOTIFICATION_SERVICE_HOST}") String host) throws Exception {
            if (playerAccountController != null)
                return playerAccountController;

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setUsername(user);
            connectionFactory.setPassword(password);
            connectionFactory.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("CL account client %d").build());

            CachingConnectionFactory springConnectionFactory = new CachingConnectionFactory(connectionFactory);

            RabbitTemplate rabbitTemplate = new RabbitTemplate();
            rabbitTemplate.setExchange(PAYMENT_EXCHANGE);
            rabbitTemplate.setRoutingKey(PAYMENT_ROUTING_KEY);
            rabbitTemplate.setConnectionFactory(springConnectionFactory);

            RabbitAdmin rabbitAdmin = new RabbitAdmin(springConnectionFactory);
            rabbitAdmin.declareExchange(new DirectExchange(PAYMENT_EXCHANGE, true, false));
            rabbitAdmin.declareQueue(new Queue(PAYMENT_QUEUE, true));
            rabbitAdmin.declareBinding(new Binding(PAYMENT_QUEUE, Binding.DestinationType.QUEUE, PAYMENT_EXCHANGE, PAYMENT_ROUTING_KEY, new HashMap<String, Object>()));

            AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
            factoryBean.setAmqpTemplate(rabbitTemplate);
            factoryBean.setServiceInterface(PlayerAccountService.class);
            factoryBean.afterPropertiesSet();
            return (PlayerAccountService) factoryBean.getObject();
        }
    }

}
