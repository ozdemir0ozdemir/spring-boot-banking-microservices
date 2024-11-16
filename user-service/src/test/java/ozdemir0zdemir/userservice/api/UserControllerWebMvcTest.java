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
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ozdemir0zdemir.userservice.domain.Gender;
import ozdemir0zdemir.userservice.domain.UserService;
import ozdemir0zdemir.userservice.request.CreateUser;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                Arguments.of(
                        new CreateUser(null,null,null,null, Gender.UNKNOWN),
                        List.of("firstName", "lastName", "email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),
                Arguments.of(
                        new CreateUser("fill",null,null,null, Gender.UNKNOWN),
                        List.of("lastName", "email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),
                Arguments.of(
                        new CreateUser("fill","fill",null,null, Gender.UNKNOWN),
                        List.of("email", "contactNo"),
                        HttpStatus.BAD_REQUEST.value()),
                Arguments.of(
                        new CreateUser("fill","fill","fill@fill.com",null, Gender.UNKNOWN),
                        List.of("contactNo"),
                        HttpStatus.BAD_REQUEST.value()),
                Arguments.of(
                        new CreateUser("fill","fill","fill@fill.com","fill", Gender.UNKNOWN),
                        List.of(),
                        HttpStatus.CREATED.value())
        );
    }


    @ParameterizedTest
    @MethodSource("createUserParameters")
    void createUserTest(CreateUser request, List<String> errorFields, int status) throws Exception{

        Mockito
                .when(userService.createUser(request))
                .thenReturn(request.email());

        ResultActions actions = mvc
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(status));

        if(status == HttpStatus.CREATED.value()){
             actions.andExpect(redirectedUrlPattern("**/api/v1/users/" + request.email()));
        }

        for(String field : errorFields){
            actions.andExpect(jsonPath("$.errors", hasKey(field)));
        }
    }
}