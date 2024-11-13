package ozdemir0zdemir.accountservice.response;

import ozdemir0zdemir.accountservice.domain.AccountStatus;
import ozdemir0zdemir.accountservice.domain.AccountType;

import java.math.BigDecimal;

public record AccountSummary(String accountNumber,
                             AccountType accountType,
                             AccountStatus accountStatus,
                             BigDecimal availableBalance) {
}
