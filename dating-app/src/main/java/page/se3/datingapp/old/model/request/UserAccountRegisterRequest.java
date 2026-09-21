package page.se3.datingapp.old.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.old.infrastructure.util.Final;
import page.se3.datingapp.old.model.struct.post.UserPost;
import page.se3.datingapp.old.model.enums.Gender;

@Schema(description = "Represents a request to register user account")
public record UserAccountRegisterRequest(
        @NotNull
        @Size(max = Final.LEN_255)
        String name,

        @NotNull
        @Size(max = Final.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = Final.LEN_255)
        String fieldOfStudy
) {

    public UserPost derivePost(String email) {
        return new UserPost(email, name, surname, gender, age, fieldOfStudy);
    }
}
