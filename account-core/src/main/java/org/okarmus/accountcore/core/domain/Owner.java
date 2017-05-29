package org.okarmus.accountcore.core.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@Value
@Accessors(fluent = true)
@Builder
class Owner {

    String name;
    String surname;
}
