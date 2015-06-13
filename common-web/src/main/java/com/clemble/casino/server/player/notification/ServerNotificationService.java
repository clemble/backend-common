package com.clemble.casino.server.player.notification;

import java.util.Collection;

import com.clemble.casino.event.Event;
import com.clemble.casino.player.PlayerAware;
import com.clemble.casino.server.NotificationService;

public interface ServerNotificationService extends NotificationService {

    <T extends PlayerAware & Event> boolean send(T event);

    <T extends PlayerAware & Event> boolean send(Collection<T> events);

    <T extends Event> boolean send(final String path, final T event);

    <T extends Event> boolean send(final String path, final Collection<T> event);

    <T extends Event> boolean send(final Collection<String> players, final T event);

    <T extends Event> boolean send(final Collection<String> players, final Collection<? extends T> event);

}
