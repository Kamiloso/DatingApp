package page.se3.datingapp.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.model.DataScheme;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.model.enums.Gender;

@Schema(description = "Represents a request to register user")
public record UserRegisterRequest(
        @NotNull
        @Size(max = DataScheme.LEN_255)
        String name,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String fieldOfStudy
) {

    public UserPost upgrade(String email) {
        return UserPost.createInitial(email, name, surname, gender, age, fieldOfStudy);
    }
}
