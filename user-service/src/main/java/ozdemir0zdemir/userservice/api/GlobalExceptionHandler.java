package ozdemir0zdemir.userservice.api;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ozdemir0zdemir.userservice.exception.EmailAlreadyRegisteredException;
import ozdemir0zdemir.userservice.exception.UserNotFoundException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Bad Request");

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        problemDetail.setProperty("errors", errors);
        problemDetail.setType(URI.create("http://localhost:8080/bad-request"));

        return ResponseEntity.status(status).body(problemDetail);
    }


    @Override
    @Nullable
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Bad Request");

        Map<String, String> errors = new HashMap<>();

        ex.getAllValidationResults().forEach(parameterValidationResult -> {
            parameterValidationResult.getResolvableErrors().forEach(err -> {
                errors.put(parameterValidationResult.getMethodParameter().getParameterName(), err.getDefaultMessage());
            });
        });

        problemDetail.setProperty("errors", errors);
        problemDetail.setType(URI.create("http://localhost:8080/bad-request"));

        return ResponseEntity.status(status).body(problemDetail);
    }




}
