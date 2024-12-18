package ozdemir0zdemir.userservice.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
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


    @GetMapping(path = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserRead> readUserByEmail(
            @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "must be formatted properly")
            @PathVariable String email) {

        UserRead response = this.userService.readUserByEmail(email);

        // TODO: inject generic response body
        return ResponseEntity.ok(response);
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createUser(@Valid @RequestBody CreateUser request) {

        String email = this.userService.createUser(request);

        // TODO: Random id may be better instead of using email
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(email)
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
