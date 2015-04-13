package com.clemble.casino.server.player.notification;

import com.clemble.casino.server.NotificationService;
import com.clemble.casino.server.event.SystemEvent;
import org.springframework.amqp.core.AmqpTemplate;

// TODO System notifications must be Durable (For payment and bonuses).
// Redis does not provide durable notifications
public interface SystemNotificationService extends NotificationService {

    public void send(SystemEvent event);

    public AmqpTemplate getTemplate();

}
