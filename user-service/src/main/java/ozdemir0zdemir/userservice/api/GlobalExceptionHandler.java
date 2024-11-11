package ozdemir0zdemir.userservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ozdemir0zdemir.userservice.exception.EmailAlreadyRegisteredException;
import ozdemir0zdemir.userservice.exception.UserNotFoundException;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: extend response with ProblemDetail

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    ResponseEntity<?> handle(EmailAlreadyRegisteredException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<?> handle(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
