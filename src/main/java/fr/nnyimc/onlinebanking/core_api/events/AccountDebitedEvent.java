package fr.nnyimc.onlinebanking.core_api.events;

import fr.nnyimc.onlinebanking.core_api.commands.BaseCommand;
import fr.nnyimc.onlinebanking.core_api.enums.AccountStatus;
import lombok.Getter;

import java.math.BigDecimal;

public class AccountDebitedEvent extends BaseCommand<String> {
    @Getter
    private BigDecimal amount;
    @Getter
    private String currency;
    @Getter
    private AccountStatus accountStatus;

    public AccountDebitedEvent(String id, String currency, BigDecimal amount, AccountStatus accountStatus) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.accountStatus = accountStatus;
    }
}
