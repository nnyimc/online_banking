package fr.nnyimc.onlinebanking.core_api.events;

import fr.nnyimc.onlinebanking.core_api.commands.BaseCommand;
import fr.nnyimc.onlinebanking.core_api.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;

public class AccountCreditedEvent extends BaseCommand<String> {
    @Getter
    private BigDecimal amount;
    @Getter
    private String currency;
    @Getter
    private AccountStatus accountStatus;

    public AccountCreditedEvent(String id, BigDecimal amount, String currency, AccountStatus accountStatus) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.accountStatus = accountStatus;
    }
}
