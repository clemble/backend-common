package com.clemble.casino.server.player.notification;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Closeable;
import java.util.Collection;

/**
 * Created by mavarazy on 5/11/15.
 */
public class SystemNotificationServiceListenerRegistrar implements ApplicationContextAware, Closeable {

    final private SystemNotificationServiceListener serviceListener;

    public SystemNotificationServiceListenerRegistrar(SystemNotificationServiceListener serviceListener){
        this.serviceListener = serviceListener;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // Step 1. Fetching all listeners from context
        Collection<SystemEventListener> systemListeners = applicationContext.getBeansOfType(SystemEventListener.class).values();
        // Step 2. Registering listeners in service listener
        for(SystemEventListener listener: systemListeners) {
            serviceListener.subscribe(listener);
        }
    }

    public void close() {
        serviceListener.close();
    }

}
