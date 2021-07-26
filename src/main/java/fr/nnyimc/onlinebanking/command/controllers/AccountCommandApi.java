package fr.nnyimc.onlinebanking.command.controllers;

import fr.nnyimc.onlinebanking.command.dto.*;
import fr.nnyimc.onlinebanking.core_api.commands.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/action/accounts")
public class AccountCommandApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCommandApi.class);
    private CommandGateway commandGateway;

    public AccountCommandApi(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> add(@RequestBody AddAccountRequestDTO createAccountRequestDTO) {
        LOGGER.info("Initial balance set to: " + createAccountRequestDTO.getInitialBalance().toString());
        String id = UUID.randomUUID().toString();
        CompletableFuture<String> completableResponse = commandGateway.send(new CreateAccountCommand(
                id,
                createAccountRequestDTO.getInitialBalance(),
                createAccountRequestDTO.getCurrency()
        ));
        LOGGER.info("Creation of account: " + id + " confirmed!");
        return completableResponse;
    }

    @PutMapping("/credit")
    public CompletableFuture<String> credit(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO) {
        LOGGER.info("Crediting the account with the following amount: " + creditAccountRequestDTO.getAmount().toString());
        CompletableFuture<String> completableResponse = commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDTO.getId(),
                creditAccountRequestDTO.getAmount(),
                creditAccountRequestDTO.getCurrency()
        ));
        return completableResponse;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debit(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO) {
        LOGGER.info("Debiting the account with the following amount: " + debitAccountRequestDTO.getAmount().toString());
        CompletableFuture<String> completableResponse = commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDTO.getId(),
                debitAccountRequestDTO.getAmount(),
                debitAccountRequestDTO.getCurrency()
        ));
        return completableResponse;
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
