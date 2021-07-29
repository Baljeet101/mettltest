package mettl.repository;

import mettl.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByAccNumber(Integer accNumber);
}
