package com.clemble.casino.server.game;


import com.clemble.casino.game.event.GameEvent;
import com.clemble.casino.server.hibernate.AbstractJsonHibernateType;

public class ClientEventHibernate extends AbstractJsonHibernateType<GameEvent>{

    public ClientEventHibernate() {
        super(GameEvent.class);
    }

}
