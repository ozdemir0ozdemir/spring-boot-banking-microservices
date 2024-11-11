package ozdemir0zdemir.userservice.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException(String email) {
        super(String.format("\"%s\" already registered.", email));
    }
}
