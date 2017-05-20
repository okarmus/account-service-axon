package org.okarmus.accountcore.core.command;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
@Builder
@Accessors(fluent = true)
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    long accountNumber;
    String ownerName;
    String ownerSurname;
}
