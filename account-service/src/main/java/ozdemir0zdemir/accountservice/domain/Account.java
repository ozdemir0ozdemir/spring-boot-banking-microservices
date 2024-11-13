package ozdemir0zdemir.accountservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import ozdemir0zdemir.accountservice.response.AccountSummary;
import ozdemir0zdemir.accountservice.request.CreateAccount;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
class Account {

    @Id
    @SequenceGenerator(name = "account_id_seq_gen", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "account_id_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Column(name = "available_balance")
    private BigDecimal availableBalance;

    @Column(name = "user_email")
    private String userEmail;

    @CreationTimestamp
    private Instant createdAt;

    static Account from(CreateAccount request) {
        return new Account()
                .setUserEmail(request.userEmail())
                .setAccountType(request.accountType())
                .setAccountStatus(AccountStatus.PENDING)
                .setAvailableBalance(BigDecimal.ZERO);
    }

    static AccountSummary toDto(Account account) {
        return new AccountSummary(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getAvailableBalance());
    }
}
