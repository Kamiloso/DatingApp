package page.se3.datingapp.models.dto;

import jakarta.validation.constraints.*;
import lombok.With;
import page.se3.datingapp.core.Const;
import page.se3.datingapp.models.enums.Gender;
import page.se3.datingapp.models.enums.Relationship;

@With
public record User(
        @NotNull
        Long id,

        @NotBlank
        @Size(max = Const.SHORT_TEXT_CAPACITY)
        String email,

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
        String fieldOfStudy,

        @NotNull
        @Size(max = Const.LONG_TEXT_CAPACITY)
        String description,

        @NotNull
        Gender targetGender,

        @NotNull
        Relationship targetRelationship,

        @NotNull
        @Size(max = Const.SHORT_TEXT_CAPACITY)
        String quizAnswers,

        @NotNull
        Boolean isParticipating,

        @NotNull
        Integer readyIndex
) {}
