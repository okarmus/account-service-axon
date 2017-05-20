package org.okarmus.accountcore.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountCreateDTO {
    private String ownerName;
    private String ownerSurname;
    private long accountNumber;
}
