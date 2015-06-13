package com.clemble.casino.server.player.notification;

import com.clemble.casino.server.event.SystemEvent;

import javax.validation.Valid;

public interface SystemEventListener<T extends SystemEvent>{

    String EXCHANGE = "clemble.system";

    void onEvent(@Valid T event);


    /**
     * Returns name of the channel, this listener is associated with
     * @return name of the channel
     */
    String getChannel();

    /**
     * Naming convension for the Queue is the name of the Channel > + name of the component
     * @return name of the Queue
     */
    String getQueueName();

}
