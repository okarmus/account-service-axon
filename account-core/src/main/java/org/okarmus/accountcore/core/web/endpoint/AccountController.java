package org.okarmus.accountcore.core.web.endpoint;

import javaslang.control.Try;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.okarmus.accountcore.core.command.CreateAccountCommand;
import org.okarmus.accountcore.core.command.DeactivateAccountCommand;
import org.okarmus.accountcore.core.command.PutMoneyCommand;
import org.okarmus.accountcore.core.command.SubstractMoneyCommand;
import org.okarmus.accountcore.core.web.dto.AccountCreateDTO;
import org.okarmus.accountcore.core.web.dto.MoneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/account")
class AccountController {

    private final CommandGateway commandGateway;
    private final ResponseHandler responseHandler;

    @Autowired
    AccountController(CommandGateway commandGateway, ResponseHandler responseHandler) {
        this.commandGateway = commandGateway;
        this.responseHandler = responseHandler;
    }

    @RequestMapping(method = POST)
    ResponseEntity<String> createAccount(@RequestBody AccountCreateDTO createDTO) {
        final CreateAccountCommand command = CreateAccountCommand.builder()
                .ownerName(createDTO.getOwnerName())
                .ownerSurname(createDTO.getOwnerSurname())
                .accountNumber(createDTO.getAccountNumber())
                .build();

        return sendCommand(command, "Account has been created");
    }

    @RequestMapping(value="/{accountNumber}/deactivate", method = DELETE)
    ResponseEntity<String> deactivateAccount(@PathVariable long accountNumber) {
        final DeactivateAccountCommand command = DeactivateAccountCommand.builder()
                .accountNumber(accountNumber)
                .build();

        return sendCommand(command, "Account has been deactivated");
    }

    @RequestMapping(value = "/{accountNumber}/put-money", method = PUT)
    ResponseEntity<String> putMoney(@PathVariable long accountNumber, @RequestBody MoneyDTO money) {
        final PutMoneyCommand command = PutMoneyCommand.builder()
                .accountNumber(accountNumber)
                .value(money.getValue())
                .build();

        return sendCommand(command, "Money has been sent");
    }

    @RequestMapping(value = "/{accountNumber}/substract-money", method = PUT)
    ResponseEntity<String> substractMoney(@PathVariable long accountNumber, @RequestBody MoneyDTO money) {
        final SubstractMoneyCommand command = SubstractMoneyCommand.builder()
                .accountNumber(accountNumber)
                .value(money.getValue())
                .build();

        return sendCommand(command, "Money has been substracted");
    }

    private <T> ResponseEntity<String> sendCommand(T command, String successMessage) {
        return responseHandler.handleCommandGatewayResponse(
                Try.of(() -> commandGateway.send(command).get()),
                successMessage
        );
    }

}
