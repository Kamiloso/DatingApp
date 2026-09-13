package page.se3.datingapp.model.request;

import jakarta.validation.constraints.*;
import page.se3.datingapp.model.Scheme;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.model.enums.Gender;

public record UserRegisterRequest(
        @NotBlank
        @Size(max = Scheme.LEN_255)
        String name,

        @NotBlank
        @Size(max = Scheme.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = Scheme.LEN_255)
        String fieldOfStudy
) {

    public UserPost upgrade(String email) {
        return UserPost.createInitial(email, name, surname, gender, age, fieldOfStudy);
    }
}
