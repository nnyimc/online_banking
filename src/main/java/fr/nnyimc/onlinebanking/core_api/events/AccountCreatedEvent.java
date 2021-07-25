package fr.nnyimc.onlinebanking.core_api.events;

import fr.nnyimc.onlinebanking.core_api.commands.BaseCommand;
import fr.nnyimc.onlinebanking.core_api.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;

public class AccountCreatedEvent extends BaseCommand<String> {
    @Getter
    private AccountStatus accountStatus;
    @Getter
    private BigDecimal initialBalance;
    @Getter
    private String currency;

    public AccountCreatedEvent(String id, String currency, BigDecimal initialBalance, AccountStatus accountStatus) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.accountStatus = accountStatus;
    }
}
