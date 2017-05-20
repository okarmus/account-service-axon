package org.okarmus.accountcore.core.event;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Builder
@Value
@Accessors(fluent = true)
public class AccountCreatedEvent {
    @TargetAggregateIdentifier
    long accountNumber;
    String ownerName;
    String ownerSurname;
}
