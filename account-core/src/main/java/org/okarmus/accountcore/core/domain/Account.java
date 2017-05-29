package org.okarmus.accountcore.core.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.okarmus.accountcore.core.command.CreateAccountCommand;
import org.okarmus.accountcore.core.command.PutMoneyCommand;
import org.okarmus.accountcore.core.command.SubstractMoneyCommand;
import org.okarmus.accountcore.core.event.AccountCreatedEvent;
import org.okarmus.accountcore.core.event.MoneyStoredEvent;
import org.okarmus.accountcore.core.event.MoneySubstractedEvent;
import org.okarmus.accountcore.core.utils.exception.AccountInactiveException;
import org.okarmus.accountcore.core.utils.exception.NotEnoughMoneyException;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@Accessors(fluent = true)
@NoArgsConstructor              //TODO it can be removed if there is a need to do so
@AllArgsConstructor
@Aggregate
public class Account {

    @AggregateIdentifier
    private Long number;
    private Owner owner;
    private long balance;
    private boolean isActive;

    @CommandHandler
    Account(CreateAccountCommand command) {
        AccountCreatedEvent event = AccountCreatedEvent.builder()
                .accountNumber(command.accountNumber())
                .ownerName(command.ownerName())
                .ownerSurname(command.ownerSurname())
                .build();

        apply(event);
    }

    @CommandHandler
    void on(PutMoneyCommand command) {
        assertNotInactive();
        apply(MoneyStoredEvent.builder()
                .accountNumber(command.accountNumber())
                .value(command.value())
                .build()
        );
    }

    @CommandHandler
    void on(SubstractMoneyCommand command) {
        assertNotInactive();
        assertEnoughMoney(command.value());

        apply(MoneySubstractedEvent.builder()
                .accountNumber(command.accountNumber())
                .value(command.value())
                .build()
        );
    }

    private void assertNotInactive() {
        if (!isActive) throw new AccountInactiveException("Account is inactive");
    }

    private void assertEnoughMoney(long toSubstract) {
        if (toSubstract > balance) throw new NotEnoughMoneyException("Not enough money on account");
    }

    //TODO add group of  @EventSourcingHandlers to be able to retrieve state from event store

    @EventHandler
    void on(AccountCreatedEvent event) {
        this.number = event.accountNumber();
        this.owner = Owner.builder().name(event.ownerName()).surname(event.ownerSurname()).build();
        this.balance = 0;
        this.isActive = true;
    }

    @EventHandler
    void on(MoneyStoredEvent event) {
        this.balance += event.value();
    }

    @EventHandler
    void on(MoneySubstractedEvent event) {
        this.balance -= event.value();
    }
}
