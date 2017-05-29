package org.okarmus.accountcore.core.command;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
@Builder
@Accessors(fluent = true)
public class SubstractMoneyCommand {

    @TargetAggregateIdentifier
    long accountNumber;
    long value;

}
