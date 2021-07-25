package fr.nnyimc.onlinebanking.core_api.events;


import fr.nnyimc.onlinebanking.core_api.commands.BaseCommand;
import fr.nnyimc.onlinebanking.core_api.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseCommand<String> {

    @Getter
    private AccountStatus accountStatus;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.accountStatus = status;
    }
}
