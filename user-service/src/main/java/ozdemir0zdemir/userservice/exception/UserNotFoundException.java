package ozdemir0zdemir.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super(String.format("User with id: \"%s\" not found", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email: \"%s\" not found", email));
    }
}
