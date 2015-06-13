package com.clemble.casino.server.event.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mavarazy on 1/13/15.
 */
public class SystemGoalBetOffEvent implements SystemGoalEvent {

    final public static String CHANNEL = "sys:goal:bet:forbid";

    final private String goalKey;

    @JsonCreator
    public SystemGoalBetOffEvent(@JsonProperty("goalKey") String goalKey) {
        this.goalKey = goalKey;
    }

    @Override
    public String getGoalKey() {
        return goalKey;
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemGoalBetOffEvent that = (SystemGoalBetOffEvent) o;

        return goalKey.equals(that.goalKey);

    }

    @Override
    public int hashCode() {
        return goalKey.hashCode();
    }

}
