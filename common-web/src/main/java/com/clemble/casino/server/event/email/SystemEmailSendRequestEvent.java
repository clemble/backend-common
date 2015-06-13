package com.clemble.casino.server.event.email;

import com.clemble.casino.server.event.TemplateAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by mavarazy on 12/8/14.
 */
public class SystemEmailSendRequestEvent implements SystemEmailEvent, TemplateAware {

    final public static String CHANNEL = "sys:email:send:player";

    final private String player;
    final private String template;
    final private Map<String, String> params;

    @JsonCreator
    public SystemEmailSendRequestEvent(@JsonProperty(PLAYER) String player, @JsonProperty("template") String template, @JsonProperty("params") Map<String, String> params) {
        this.player = player;
        this.template = template;
        this.params = params;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemEmailSendRequestEvent that = (SystemEmailSendRequestEvent) o;

        if (!player.equals(that.player)) return false;
        if (!template.equals(that.template)) return false;
        return params.equals(that.params);

    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + template.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return player + " > " + CHANNEL;
    }

}
