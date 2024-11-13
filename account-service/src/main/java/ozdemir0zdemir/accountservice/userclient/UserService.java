package ozdemir0zdemir.accountservice.userclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record UserService(UserClient client) {

    public Optional<User> getUserByEmail(String email) {

        ResponseEntity<User> response = client.readUserByEmail(email);
        if(response.getStatusCode().is2xxSuccessful()){
            return Optional.of(response.getBody());
        }

        return Optional.empty();
    }
}
