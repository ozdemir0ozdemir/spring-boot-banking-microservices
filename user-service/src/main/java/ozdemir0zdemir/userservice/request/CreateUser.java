package ozdemir0zdemir.userservice.request;

import ozdemir0zdemir.userservice.domain.Gender;

// TODO: Validation
public record CreateUser(String firstName,
                         String lastName,
                         String email,
                         String contactNo,
                         Gender gender) {
}
