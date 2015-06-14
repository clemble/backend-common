package com.clemble.casino.server.event.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
public class SystemPlayerConnectedEvent implements SystemPlayerEvent {

    final public static String CHANNEL = "sys:player:connected";

    final private String player;
    final private String connected;

    @JsonCreator
    public SystemPlayerConnectedEvent(
        @JsonProperty(PLAYER) String player,
        @JsonProperty("connected") String connected) {
        this.player = player;
        this.connected = connected;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    public String getConnected() {
        return connected;
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemPlayerConnectedEvent)) return false;

        SystemPlayerConnectedEvent that = (SystemPlayerConnectedEvent) o;

        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        return !(connected != null ? !connected.equals(that.connected) : that.connected != null);

    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (connected != null ? connected.hashCode() : 0);
        return result;
    }

}
