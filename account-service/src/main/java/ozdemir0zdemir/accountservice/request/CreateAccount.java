package ozdemir0zdemir.accountservice.request;

import ozdemir0zdemir.accountservice.domain.AccountType;

public record CreateAccount(String userEmail,
                            AccountType accountType) {
}
