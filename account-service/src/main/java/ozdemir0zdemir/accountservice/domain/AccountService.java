package ozdemir0zdemir.accountservice.domain;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ozdemir0zdemir.accountservice.response.AccountSummary;
import ozdemir0zdemir.accountservice.exception.AccountNotFoundException;
import ozdemir0zdemir.accountservice.exception.UserNotFoundException;
import ozdemir0zdemir.accountservice.request.CreateAccount;
import ozdemir0zdemir.accountservice.userclient.User;
import ozdemir0zdemir.accountservice.userclient.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository repository;
    private final UserService userService;

    public String createAccount(CreateAccount request) {

        // TODO: Parallel request
        throwIfUserNotFound(request.userEmail());
        throwIfEmailAndAccountTypeExists(request.userEmail(), request.accountType());

        // TODO: Generate Proper Account Number
        Account account = Account.from(request)
                .setAccountNumber("ACN."+UUID.randomUUID());

        return this.repository.save(account).getAccountNumber();
    }

    public AccountSummary getAccountByAccountNumber(String accountNumber) {
        return this.repository.findByAccountNumber(accountNumber)
                .map(Account::toDto)
                .orElseThrow(() -> AccountNotFoundException.forAccountNumber(accountNumber));
    }

    // Check user has an account same type in request
    private void throwIfEmailAndAccountTypeExists(String email, AccountType accountType) {
        this.repository
                .findByUserEmailAndAccountType(email, accountType)
                .ifPresent(_ -> {
                    throw new AccountNotFoundException(email);
                });
    }

    // Check User by email using user-service
    private User throwIfUserNotFound(String email) {
        return userService
                .getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

}
