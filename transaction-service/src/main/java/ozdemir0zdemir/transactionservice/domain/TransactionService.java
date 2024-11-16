package ozdemir0zdemir.transactionservice.domain;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository repository;

    public void addTransaction(Transaction request) {

        // Check account number using feign client

        // Transaction Type == DEPOSIT
        // add account balance using feign client

        // Transaction Type == WITHDRAWAL
        // Check account status is active
        // Check account balance is sufficient to withdraw amount
        // Negate transaction amount
        // add account balance using feign client

        // Create new transaction to save
        // set transaction number to random uuid
        // transaction status ?

        // If transaction is completed then update account balance

        // return saved transaction number
    }

    public void internalTransactions(List<Transaction> transactions, String transactionNumber) {

        // For Each Transaction
        // Set Type INTERNAL_TRANSFER
        // Set Transaction Status ?
        // Set Transaction Numbers to transactionNumber
        // Save all transactions
    }

    public void getTransactionsByAccountNumber(String accountNumber) {
        // return all transactions of an account
    }

    public void getTransactionsByTransactionNumber(String transactionNumber) {
        // return all transactions bond with the transactionNumber
    }
}
