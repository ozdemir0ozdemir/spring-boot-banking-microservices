package ozdemir0zdemir.transactionservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_id_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "transaction_id_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "transaction_id")
    private Long id;

    private String transactionNumber;
    private String accountNumber;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private BigDecimal amount;

    @CreationTimestamp
    private Instant transactionDate;
}
