package page.se3.datingapp.infrastructure.controller.rest.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.Final;
import page.se3.datingapp.domain.enums.Gender;

@Schema(description = "A request to register user account")
public record UserAccountRegisterDto(
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
        String fieldOfStudy,

        @NotNull
        @Size(max = Final.LEN_2047)
        String description,

        @NotNull
        Relationship targetRelationshipMan,

        @NotNull
        Relationship targetRelationshipWoman,

        @NotNull
        Relationship targetRelationshipOther,

        @NotNull
        @Size(max = Final.LEN_2047)
        String quizAnswers,

        @NotNull
        Boolean isReadyForMatching
) {}
