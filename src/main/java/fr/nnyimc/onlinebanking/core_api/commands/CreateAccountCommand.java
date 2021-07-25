package fr.nnyimc.onlinebanking.core_api.commands;

import lombok.Getter;

import java.math.BigDecimal;

public class CreateAccountCommand extends BaseCommand<String> {
    @Getter
    private BigDecimal initialBalance;
    @Getter
    private String currency;

    public CreateAccountCommand(String id, BigDecimal initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
