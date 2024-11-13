package ozdemir0zdemir.accountservice.userclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service", url = "http://localhost:8082/api/v1/users")
interface UserClient {

    @GetMapping(path = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> readUserByEmail(@PathVariable String email);
}
