package page.se3.datingapp.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.With;
import page.se3.datingapp.core.Const;
import page.se3.datingapp.models.enums.Gender;

@With
public record UserRegisterRequest(
        @NotBlank
        @Size(max = Const.SHORT_TEXT_CAPACITY)
        String name,

        @NotBlank
        @Size(max = Const.SHORT_TEXT_CAPACITY)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @Min(Const.MIN_AGE)
        Integer age,

        @NotBlank
        @Size(max = Const.SHORT_TEXT_CAPACITY)
        String fieldOfStudy
) {}
