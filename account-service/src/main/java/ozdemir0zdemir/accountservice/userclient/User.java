package ozdemir0zdemir.accountservice.userclient;

public record User(String email,
                   String contactNo,
                   Status status,
                   String firstName,
                   String lastName,
                   Gender gender) {
}
