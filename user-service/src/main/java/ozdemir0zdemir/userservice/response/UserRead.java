package ozdemir0zdemir.userservice.response;

import ozdemir0zdemir.userservice.domain.Gender;
import ozdemir0zdemir.userservice.domain.Status;


public record UserRead(String email,
                       String contactNo,
                       Status status,
                       String firstName,
                       String lastName,
                       Gender gender) {
}
