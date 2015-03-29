package com.clemble.casino.server.player.notification;

import com.clemble.casino.event.Event;
import com.clemble.casino.notification.PlayerNotificationConvertible;
import com.clemble.casino.player.PlayerAware;
import com.clemble.casino.player.service.PlayerNotificationService;
import com.clemble.casino.post.PlayerPostConvertible;
import com.clemble.casino.server.event.notification.SystemNotificationAddEvent;
import com.clemble.casino.server.event.post.SystemPostAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by mavarazy on 11/29/14.
 */
abstract class AbstractServerNotificationService implements ServerNotificationService {

    final protected static Logger LOG = LoggerFactory.getLogger(PlayerNotificationService.class);

    final private SystemNotificationService systemNotificationService;

    public AbstractServerNotificationService(SystemNotificationService systemNotificationService) {
        this.systemNotificationService = systemNotificationService;
    }

    @Override
    final public <T extends Event> boolean send(final Collection<String> players, final T event) {
        LOG.trace("Sending {} to {}", event, players);
        // Step 1. Notifying specific player
        boolean fullSuccess = true;
        for(String player: players)
            fullSuccess = send(player, event) & fullSuccess;
        return fullSuccess;
    }

    @Override
    final public <T extends Event> boolean send(final String player, final Collection<T> events) {
        boolean fullSuccess = true;
        for (T event : events)
            fullSuccess = send(player, event) & fullSuccess;
        return fullSuccess;
    }

    @Override
    final public <T extends Event> boolean send(final Collection<String> players, final Collection<? extends T> events) {
        // Step 1. Notifying each event one after another
        boolean fullSuccess = true;
        for (T event : events)
            fullSuccess = send(players, event) & fullSuccess;
        return fullSuccess;
    }

    @Override
    final public <T extends Event> boolean send(String player, T event) {
        LOG.trace("Sending {} to {}", event, player);
        // Step 1. Adding to notifications player
        // Check 1. Event is convertible to notification
        if (event instanceof PlayerNotificationConvertible && !PlayerAware.DEFAULT_PLAYER.equals(player)) {
            systemNotificationService.send(new SystemNotificationAddEvent(((PlayerNotificationConvertible) event).toNotification()));
        }
        // Check 2. Event is convertible to post
        if (event instanceof PlayerPostConvertible && !PlayerAware.DEFAULT_PLAYER.equals(player)) {
            systemNotificationService.send(new SystemPostAddEvent(((PlayerPostConvertible) event).toPost()));
        }
        // Step 2. Do send notification
        return doSend(player, event);
    }

    @Override
    final public <T extends PlayerAware & Event> boolean send(Collection<T> events) {
        // Step 1. Sanity check
        if (events == null || events.size() == 0)
            return true;
        // Step 2. Checking values in Event
        boolean notifiedAll = true;
        for (T event : events) {
            notifiedAll = send(event) & notifiedAll;
        }
        return notifiedAll;
    }

    @Override
    final public <T extends PlayerAware & Event> boolean send(T event) {
        return event != null ? send(event.getPlayer(), event) : true;
    }

    abstract protected <T extends Event> boolean doSend(final String player, final T event);

}
