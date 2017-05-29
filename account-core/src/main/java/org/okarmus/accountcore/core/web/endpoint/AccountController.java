package org.okarmus.accountcore.core.web.endpoint;

import javaslang.control.Try;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.okarmus.accountcore.core.command.CreateAccountCommand;
import org.okarmus.accountcore.core.command.PutMoneyCommand;
import org.okarmus.accountcore.core.web.dto.AccountCreateDTO;
import org.okarmus.accountcore.core.web.dto.MoneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
class AccountController {

    private final CommandGateway commandGateway;

    @Autowired
    AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<String> createAccount(@RequestBody AccountCreateDTO createDTO) {
        final CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                .ownerName(createDTO.getOwnerName())
                .ownerSurname(createDTO.getOwnerSurname())
                .accountNumber(createDTO.getAccountNumber())
                .build();

        return Try.of(() -> commandGateway.send(createAccountCommand).get())
            .map(result -> successResponse("Account has been created"))
            .getOrElseGet(this::errorResponse);
    }

    @RequestMapping(value = "/{accountNumber}/put-money", method = RequestMethod.POST)
    ResponseEntity<String> putMoney(@PathVariable long accountNumber, @RequestBody MoneyDTO money) {
        final PutMoneyCommand command = PutMoneyCommand.builder()
                .accountNumber(accountNumber)
                .value(money.getValue())
                .build();

        return Try.of(() -> commandGateway.send(command).get())
                .map(result -> successResponse("Money has been sent"))
                .getOrElseGet(this::errorResponse);
    }

    private ResponseEntity<String> successResponse(String message) {
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    private ResponseEntity<String> errorResponse(Throwable throwable) {
        throwable.printStackTrace();
        return new ResponseEntity<>("Error occurred: " + throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
