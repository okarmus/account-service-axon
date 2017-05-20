package org.okarmus.accountcore;

import org.axonframework.common.IdentifierFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountCoreConfiguration {

    @Bean
    IdentifierFactory identifierFactory() {
        return IdentifierFactory.getInstance();
    }

}
