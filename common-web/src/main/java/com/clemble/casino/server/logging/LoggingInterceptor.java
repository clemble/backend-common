package com.clemble.casino.server.logging;

import com.clemble.casino.goal.GoalAware;
import com.clemble.casino.payment.PaymentTransactionAware;
import com.clemble.casino.player.PlayerAware;
import org.jboss.logging.MDC;

/**
 * Created by mavarazy on 4/16/15.
 */
public interface LoggingInterceptor {

    default void putInMDC(Object event) {
        if (event instanceof PlayerAware) {
            MDC.put(PlayerAware.PLAYER, ((PlayerAware) event).getPlayer());
        }
        if (event instanceof GoalAware) {
            MDC.put(GoalAware.GOAL_KEY, ((GoalAware) event).getGoalKey());
        }
        if (event instanceof PaymentTransactionAware) {
            MDC.put(PaymentTransactionAware.TRANSACTION_KEY, ((PaymentTransactionAware) event).getTransactionKey());
        }
    }

    default void removeFromMDC() {
        MDC.remove(PlayerAware.PLAYER);
        MDC.remove(GoalAware.GOAL_KEY);
        MDC.remove(PaymentTransactionAware.TRANSACTION_KEY);
    }
}
