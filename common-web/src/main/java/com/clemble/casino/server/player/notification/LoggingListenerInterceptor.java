package com.clemble.casino.server.player.notification;

import com.clemble.casino.server.event.SystemEvent;
import com.clemble.casino.server.logging.LoggingInterceptor;

/**
 * Created by mavarazy on 4/16/15.
 */
public class LoggingListenerInterceptor implements LoggingInterceptor, SystemEventListenerInterceptor{

    @Override
    public void preHandle(SystemEvent event) {
        putInMDC(event);
    }

    @Override
    public void postHandle(SystemEvent event) {
        removeFromMDC();
    }

}
