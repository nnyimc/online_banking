package fr.nnyimc.onlinebanking.query.service;

import fr.nnyimc.onlinebanking.core_api.enums.OperationType;
import fr.nnyimc.onlinebanking.core_api.events.*;
import fr.nnyimc.onlinebanking.query.entities.*;
import fr.nnyimc.onlinebanking.query.repository.*;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class EventHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerService.class);
    private AccountRepository accountRepository;
    private AccountOperationRepository accountOperationRepository;

    public EventHandlerService(AccountRepository accountRepository, AccountOperationRepository accountOperationRepository) {
        this.accountRepository = accountRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        LOGGER.info("Handling the query related to the account creation event...");
        Account account = new Account();

        account.setId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getInitialBalance());
        account.setCurrency(accountCreatedEvent.getCurrency());
        account.setAccountStatus(accountCreatedEvent.getAccountStatus());

        accountRepository.save(account);
        LOGGER.info("The new account is now registered!!");
    }

    @EventHandler
    @Transactional
    public void on(AccountCreditedEvent accountCreditedEvent) {
        LOGGER.info("Handling the query related to the crediting operation...");

        LOGGER.info("Retrieving the target account...");
        Account account = accountRepository.getById(accountCreditedEvent.getId());

        AccountOperation accountOperation = new AccountOperation();

        accountOperation.setAmount(accountCreditedEvent.getAmount());
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(account);
        LOGGER.info("Registering a crediting operation for the target account...");
        accountOperationRepository.save(accountOperation);

        LOGGER.info("Saving the target account new balance...");
        account.setBalance(account.getBalance().add(accountCreditedEvent.getAmount()));
        accountRepository.save(account);


    }

    @EventHandler
    @Transactional
    public void on(AccountDebitedEvent accountDebitedEvent) {
        LOGGER.info("Handling the query related to the debiting operation...");

        LOGGER.info("Retrieving the target account...");
        Account account = accountRepository.getById(accountDebitedEvent.getId());

        AccountOperation accountOperation = new AccountOperation();

        accountOperation.setAmount(accountDebitedEvent.getAmount());
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(account);
        LOGGER.info("Registering a crediting operation for the target account...");
        accountOperationRepository.save(accountOperation);

        LOGGER.info("Saving the target account new balance...");
        account.setBalance(account.getBalance().subtract(accountOperation.getAmount()));

    }
}
