package com.clemble.casino.server.player.notification;

import com.clemble.casino.server.event.SystemEvent;

/**
 * Created by mavarazy on 4/16/15.
 */
public interface SystemEventListenerInterceptor {

    public void preHandle(SystemEvent event);

    public void postHandle(SystemEvent event);

}
