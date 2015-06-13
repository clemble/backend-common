package com.clemble.casino.server.player.notification;

import java.util.Collection;

public interface RedisSubscribersAware {

    Collection<String> getChannels();

    Collection<String> getPatterns();

}
