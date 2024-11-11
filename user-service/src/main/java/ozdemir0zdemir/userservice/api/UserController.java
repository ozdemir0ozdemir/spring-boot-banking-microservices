package ozdemir0zdemir.userservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ozdemir0zdemir.userservice.domain.UserService;
import ozdemir0zdemir.userservice.request.CreateUser;
import ozdemir0zdemir.userservice.response.UserRead;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
record UserController(UserService userService) {

    // TODO: Validation
    @PostMapping
    ResponseEntity<?> createUser(@RequestBody CreateUser request) {

        String email = this.userService.createUser(request);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/users/{email}")
                .buildAndExpand(email)
                .toUri();

        // TODO: inject generic response body?
        return ResponseEntity.created(location).build();
    }

    // TODO: Validation
    @GetMapping("/{email}")
    ResponseEntity<UserRead> readUserByEmail(@PathVariable String email) {

        UserRead userRead = this.userService.readUserByEmail(email);

        // TODO: inject generic response body
        return ResponseEntity.ok(userRead);
    }

}
