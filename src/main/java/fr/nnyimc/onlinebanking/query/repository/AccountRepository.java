package fr.nnyimc.onlinebanking.query.repository;

import fr.nnyimc.onlinebanking.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
