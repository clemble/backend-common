package com.clemble.casino.server.event.log;

import com.clemble.casino.log.ClientError;
import com.clemble.casino.player.PlayerAware;
import com.clemble.casino.server.event.SystemEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mavarazy on 5/15/15.
 */
public class SystemClientErrorEvent implements SystemEvent, PlayerAware {

    final public static String CHANNEL = "sys:log:event";

    final private String player;
    final private ClientError error;

    @JsonCreator
    public SystemClientErrorEvent(
            @JsonProperty(PLAYER) String player,
            @JsonProperty("error") ClientError error) {
        this.player = player;
        this.error = error;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    public ClientError getError() {
        return error;
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemClientErrorEvent)) return false;

        SystemClientErrorEvent that = (SystemClientErrorEvent) o;

        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }

}
