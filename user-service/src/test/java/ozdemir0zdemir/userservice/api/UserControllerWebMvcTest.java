package ozdemir0zdemir.userservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ozdemir0zdemir.userservice.domain.Gender;
import ozdemir0zdemir.userservice.domain.Status;
import ozdemir0zdemir.userservice.domain.UserService;
import ozdemir0zdemir.userservice.request.CreateUser;
import ozdemir0zdemir.userservice.response.UserRead;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({UserController.class, GlobalExceptionHandler.class})
class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private static Stream<Arguments> createUserParameters() {
        return Stream.of(
                // When all parameters are invalid
                Arguments.of(
                        new CreateUser(null, null, null, null, null),
                        List.of("firstName", "lastName", "email", "contactNo", "gender"),
                        HttpStatus.BAD_REQUEST.value()),

                // When firstName, lastName, email, contactNo are invalid
                Arguments.of(
                        new CreateUser(null, null, null, null, Gender.UNKNOWN),
                        List.of("firstName", "lastName", "email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),

                // When lastName, email, contactNo are invalid
                Arguments.of(
                        new CreateUser("fill", null, null, null, Gender.UNKNOWN),
                        List.of("lastName", "email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),

                // When email, contactNo are invalid
                Arguments.of(
                        new CreateUser("fill", "fill", null, null, Gender.UNKNOWN),
                        List.of("email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),

                // When contactNo is invalid
                Arguments.of(
                        new CreateUser("fill", "fill", "fill@fill.com", null, Gender.UNKNOWN),
                        List.of("contactNo"),
                        HttpStatus.BAD_REQUEST.value()),

                // When firstName is invalid size
                Arguments.of(
                        new CreateUser("aa", "fill", "fill@fill.com", "fill", Gender.UNKNOWN),
                        List.of("firstName"),
                        HttpStatus.BAD_REQUEST.value()),

                // When lastName is invalid size
                Arguments.of(
                        new CreateUser("fill", "aa", "fill@fill.com", "fill", Gender.UNKNOWN),
                        List.of("lastName"),
                        HttpStatus.BAD_REQUEST.value()),

                // When email is not properly formatted
                Arguments.of(
                        new CreateUser("fill", "fill", "", "fill", Gender.UNKNOWN),
                        List.of("email"),
                        HttpStatus.BAD_REQUEST.value()),

                // When contactNo is invalid - blank
                Arguments.of(
                        new CreateUser("fill", "fill", "fill@fill.com", "", Gender.UNKNOWN),
                        List.of("contactNo"),
                        HttpStatus.BAD_REQUEST.value()),

                // When all parameters are valid
                Arguments.of(
                        new CreateUser("fill", "fill", "fill@fill.com", "fill", Gender.UNKNOWN),
                        List.of(),
                        HttpStatus.CREATED.value())
        );
    }

    private static Stream<Arguments> readUserByEmailParameters() {
        return Stream.of(
                Arguments.of("example@example.com", HttpStatus.OK.value()),
                Arguments.of("example@.com", HttpStatus.BAD_REQUEST.value()),
                Arguments.of("@example.com", HttpStatus.BAD_REQUEST.value()),
                Arguments.of("example@example.c", HttpStatus.BAD_REQUEST.value()),
                Arguments.of(" ", HttpStatus.BAD_REQUEST.value())
        );
    }


    @ParameterizedTest
    @MethodSource("createUserParameters")
    void createUserTest(CreateUser request, List<String> errorFields, int status) throws Exception {

        Mockito
                .when(userService.createUser(request))
                .thenReturn(request.email());

        ResultActions actions = mvc
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(status));

        if (status == HttpStatus.CREATED.value()) {
            actions.andExpect(redirectedUrlPattern("**/api/v1/users/" + request.email()));
        }

        for (String field : errorFields) {
            actions.andExpect(jsonPath("$.errors", hasKey(field)));
        }
    }


    @ParameterizedTest
    @MethodSource("readUserByEmailParameters")
    void readUserByEmailTest(String email, int status) throws Exception {

        Mockito
                .when(userService.readUserByEmail(email))
                .thenReturn(new UserRead(email,
                        "123456",
                        Status.PENDING,
                        "name",
                        "lastname",
                        Gender.UNKNOWN));

        mvc
                .perform(get("/api/v1/users/{email}", email))
                .andExpect(status().is(status));
    }
}