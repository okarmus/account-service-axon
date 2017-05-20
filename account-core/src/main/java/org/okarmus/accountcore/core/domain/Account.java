package org.okarmus.accountcore.core.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.okarmus.accountcore.core.command.CreateAccountCommand;
import org.okarmus.accountcore.core.event.AccountCreatedEvent;


@Accessors(fluent = true)
@NoArgsConstructor              //TODO it can be removed if there is a need to do so
@AllArgsConstructor
@Aggregate
public class Account {
    @AggregateIdentifier
    private long number;
    private Owner owner;
    private long balance;
    private boolean isActive;

    @CommandHandler
    public Account(CreateAccountCommand command) {      //TODO does it have to be public ?
        AccountCreatedEvent event = AccountCreatedEvent.builder()
                .accountNumber(command.accountNumber())
                .ownerName(command.ownerName())
                .ownerSurname(command.ownerSurname())
                .build();

        AggregateLifecycle.apply(event);
    }

    //TODO add group of  @EventSourcingHandlers to be able to retrieve state from event store


    @EventHandler
    public void on(AccountCreatedEvent event) {
        this.number = event.accountNumber();
        this.owner = Owner.builder().name(event.ownerName()).surname(event.ownerSurname()).build();
        this.balance = 0;
        this.isActive = true;           //we can set is as false in the begining
    }
}
