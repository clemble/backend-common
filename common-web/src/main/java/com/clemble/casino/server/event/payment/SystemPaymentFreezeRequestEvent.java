package com.clemble.casino.server.event.payment;

import com.clemble.casino.money.Money;
import com.clemble.casino.money.Operation;
import com.clemble.casino.payment.PaymentOperation;
import com.clemble.casino.payment.PendingTransaction;
import com.clemble.casino.player.PlayerAware;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by mavarazy on 16/10/14.
 */
public class SystemPaymentFreezeRequestEvent implements SystemPaymentEvent {

    final public static String CHANNEL = "sys:payment:freeze:request";

    @NotNull
    final private String transactionKey;
    @NotNull
    final private PendingTransaction transaction;

    @JsonCreator
    public SystemPaymentFreezeRequestEvent(
        @JsonProperty(TRANSACTION_KEY) String transactionKey,
        @JsonProperty("transaction") PendingTransaction transaction
    ) {
        this.transactionKey = transactionKey;
        this.transaction = transaction;
    }

    @Override
    public String getTransactionKey() {
        return transactionKey;
    }

    @Valid
    public PendingTransaction getTransaction() {
        return transaction;
    }

    @Override
    public String getChannel() {
        return CHANNEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemPaymentFreezeRequestEvent that = (SystemPaymentFreezeRequestEvent) o;

        return !(transaction != null ? !transaction.equals(that.transaction) : that.transaction != null);

    }

    @Override
    public int hashCode() {
        return transaction != null ? transaction.hashCode() : 0;
    }

    @Override
    public String toString() {
        return transaction.getTransactionKey() + " > " + CHANNEL;
    }

    public static SystemPaymentFreezeRequestEvent create(String key, String player, Money amount) {
        Set<PaymentOperation> operations = ImmutableSet.of(
                new PaymentOperation(player, amount, Operation.Credit),
                new PaymentOperation(PlayerAware.DEFAULT_PLAYER, amount, Operation.Debit)
        );
        return new SystemPaymentFreezeRequestEvent(key, new PendingTransaction(key, operations, null));
    }
}

