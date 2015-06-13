package com.clemble.casino.server.player.notification;

import com.clemble.casino.server.event.SystemEvent;
import com.clemble.casino.server.player.notification.SystemEventListener;

public interface SystemNotificationServiceListener {

    void subscribe(SystemEventListener<? extends SystemEvent> messageListener);

    void unsubscribe(SystemEventListener<? extends SystemEvent> messageListener);

    void close();

}
