package ozdemir0zdemir.transactionservice.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);
    Optional<Transaction> findByTransactionNumber(String transactionNumber);
}
