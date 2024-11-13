package ozdemir0zdemir.accountservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserEmailAndAccountType(String userEmail, AccountType accountType);

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByUserEmail(String userEmail);
}
