package com.clemble.casino.server.event.goal;

import com.clemble.casino.goal.lifecycle.construction.GoalConstruction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mavarazy on 9/20/14.
 */
public class SystemGoalStartedEvent implements SystemGoalEvent {

    final public static String CHANNEL = "sys:goal:started";

    final private String goalKey;
    final private GoalConstruction construction;

    @JsonCreator
    public SystemGoalStartedEvent(@JsonProperty("goalKey") String goalKey, @JsonProperty("construction") GoalConstruction construction) {
        this.goalKey = goalKey;
        this.construction = construction;
    }

    public GoalConstruction getConstruction() {
        return construction;
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

        SystemGoalStartedEvent that = (SystemGoalStartedEvent) o;

        if (!goalKey.equals(that.goalKey)) return false;
        if (!construction.equals(that.construction)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = goalKey.hashCode();
        result = 31 * result + construction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return goalKey + " > " + CHANNEL;
    }

}
