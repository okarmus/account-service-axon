package org.okarmus.accountcore.web.endpoint;

import javaslang.control.Try;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.okarmus.accountcore.core.command.CreateAccountCommand;
import org.okarmus.accountcore.web.dto.AccountCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/account")
class AccountController {

    private final CommandGateway commandGateway;

    @Autowired
    AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<String> createAccount(@RequestBody AccountCreateDTO createDTO) {
        final CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                .ownerName(createDTO.getOwnerName())
                .ownerSurname(createDTO.getOwnerSurname())
                .accountNumber(createDTO.getAccountNumber())
                .build();

        return Try.of(() -> commandGateway.send(createAccountCommand).get())
            .map(obj -> successResponse())
            .getOrElseGet(this::errorResponse);
    }

    private ResponseEntity<String> successResponse() {
        return new ResponseEntity<>("Account has been created", HttpStatus.CREATED);
    }

    private ResponseEntity<String> errorResponse(Throwable throwable) {
        throwable.printStackTrace();
        return new ResponseEntity<>("Error occurred: " + throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
