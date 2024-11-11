package ozdemir0zdemir.userservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ozdemir0zdemir.userservice.request.CreateUser;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
class UserProfile {

    @Id
    @SequenceGenerator(name = "user_profile_id_seq_gen", sequenceName = "user_profile_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_profile_id_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    static UserProfile from(CreateUser request) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(request.firstName());
        userProfile.setLastName(request.lastName());
        userProfile.setGender(request.gender());

        return userProfile;
    }
}
