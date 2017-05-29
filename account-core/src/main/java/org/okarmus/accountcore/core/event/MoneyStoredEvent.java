package org.okarmus.accountcore.core.event;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
@Accessors(fluent = true)
@Builder
public class MoneyStoredEvent {

    @TargetAggregateIdentifier
    long accountNumber;
    long value;
}
