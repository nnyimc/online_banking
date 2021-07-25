package fr.nnyimc.onlinebanking.command.aggregates;

import fr.nnyimc.onlinebanking.command.exceptions.InsufficientBalanceException;
import fr.nnyimc.onlinebanking.core_api.commands.*;
import fr.nnyimc.onlinebanking.core_api.enums.AccountStatus;
import fr.nnyimc.onlinebanking.core_api.events.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.*;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.*;

import java.math.BigDecimal;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountAggregate.class);
    @AggregateIdentifier
    private String id;
    private BigDecimal balance;
    private String currency;
    private AccountStatus accountStatus;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        LOGGER.info("Received a new create account command...");
        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                        createAccountCommand.getId(),
                        createAccountCommand.getCurrency(),
                        createAccountCommand.getInitialBalance(),
                        AccountStatus.CREATED
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        LOGGER.info("Confirming account creation...");
        this.id = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.accountStatus = accountCreatedEvent.getAccountStatus();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                accountCreatedEvent.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        LOGGER.info("Confirming account activation...");
        this.accountStatus = accountActivatedEvent.getAccountStatus();
    }



    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand) {
        LOGGER.info("Received a new credit account command...");
        AggregateLifecycle.apply(
                new AccountCreditedEvent(
                        creditAccountCommand.getId(),
                        creditAccountCommand.getAmount(),
                        creditAccountCommand.getCurrency(),
                        AccountStatus.CREDITED
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent) {
        LOGGER.info("Confirming account crediting...");
        this.balance = this.balance.add(accountCreditedEvent.getAmount()) ;
        this.accountStatus = accountCreditedEvent.getAccountStatus();
    }



    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand) {
        LOGGER.info("Received a new credit account command...");
        if( this.balance.subtract(debitAccountCommand.getAmount()).doubleValue()  < 0 ) {
            throw new InsufficientBalanceException("Insufficient funds!");
        }
        AggregateLifecycle.apply(
                new AccountDebitedEvent(
                        debitAccountCommand.getId(),
                        debitAccountCommand.getCurrency(),
                        debitAccountCommand.getAmount(),
                        AccountStatus.DEBITED
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        LOGGER.info("Confirming account debiting...");
        this.balance = this.balance.subtract(accountDebitedEvent.getAmount()) ;
        this.accountStatus = accountDebitedEvent.getAccountStatus();
    }
}