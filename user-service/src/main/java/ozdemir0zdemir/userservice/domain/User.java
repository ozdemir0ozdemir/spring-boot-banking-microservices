package ozdemir0zdemir.userservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ozdemir0zdemir.userservice.request.CreateUser;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
class User {

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq")
    @GeneratedValue(generator = "users_id_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    // TODO: PATTERN VALIDATION
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact_no", unique = true)
    private String contactNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // TODO: Lazy and do projection
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id", unique = true)
    private UserProfile userProfile;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    static User from(CreateUser request) {

        return new User()
                .setEmail(request.email())
                .setContactNo(request.contactNo())
                .setUserProfile(UserProfile.from(request));
    }
}
