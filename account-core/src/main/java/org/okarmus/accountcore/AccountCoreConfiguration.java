package org.okarmus.accountcore;

import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AccountCoreConfiguration {

    @Bean
    IdentifierFactory identifierFactory() {
        return IdentifierFactory.getInstance();
    }

    @Bean
    EventStore eventStore() {
        return  new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }

}
