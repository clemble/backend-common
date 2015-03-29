package com.clemble.casino.base;

import com.clemble.casino.ActionLatch;
import com.clemble.casino.lifecycle.management.event.action.PlayerAction;
import com.clemble.casino.player.event.PlayerEvent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mavarazy on 8/20/14.
 */
public class JavaActionLatchService implements ActionLatchService {

    final private ConcurrentHashMap<String, ActionLatch> LATCH_MAP = new ConcurrentHashMap<>();

    @Override
    public ActionLatch save(String key, ActionLatch latch) {
        return LATCH_MAP.putIfAbsent(key, latch);
    }

    @Override
    public ActionLatch get(String key) {
        return LATCH_MAP.get(key);
    }

    @Override
    public ActionLatch update(PlayerAction<?> event) {
        return LATCH_MAP.computeIfPresent(event.getKey(), (k, v) -> v.put(event));
    }
}
