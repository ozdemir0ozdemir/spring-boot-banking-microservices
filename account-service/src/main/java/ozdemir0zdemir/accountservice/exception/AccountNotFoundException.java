package ozdemir0zdemir.accountservice.exception;

public class AccountNotFoundException extends RuntimeException{



    public AccountNotFoundException(String message) {
        super(message);
    }

    public static AccountNotFoundException forEmail(String email) {
        return new AccountNotFoundException(String.format("Account with user email: \"%s\" not found", email));
    }

    public static AccountNotFoundException forAccountNumber(String accountNumber) {
        return new AccountNotFoundException(String.format("Account with number : \"%s\" not found", accountNumber));
    }



}
