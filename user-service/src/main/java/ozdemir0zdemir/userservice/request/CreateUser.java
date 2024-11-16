package ozdemir0zdemir.userservice.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ozdemir0zdemir.userservice.domain.Gender;
import ozdemir0zdemir.userservice.utils.GenderPatternDeserializer;

public record CreateUser(@NotNull
                         @Size(min = 3, message = "must be at least 3 chars long")
                         String firstName,

                         @NotNull
                         @Size(min = 3, message = "must be at least 3 chars long")
                         String lastName,

                         @NotNull
                         @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "not properly formatted")
                         String email,

                         @NotNull
                         @NotBlank(message = "must not be blank")
                         String contactNo,

                         @NotNull
                         @JsonDeserialize(using = GenderPatternDeserializer.class)
                         Gender gender) {
}
