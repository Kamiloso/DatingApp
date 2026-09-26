package page.se3.datingapp.infrastructure.rest.controller.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.infrastructure.rest.Template;

@Schema(description = "Represents user account")
public record UserAccountDto(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Template.LEN_255)
        String email,

        @NotNull
        @Size(max = Template.LEN_255)
        String name,

        @NotNull
        @Size(max = Template.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = Template.LEN_255)
        String fieldOfStudy,

        @NotNull
        @Size(max = Template.LEN_2047)
        String description,

        @NotNull
        Relationship targetRelationshipMan,

        @NotNull
        Relationship targetRelationshipWoman,

        @NotNull
        Relationship targetRelationshipOther,

        @NotNull
        @Size(max = Template.LEN_2047)
        String quizAnswers,

        @NotNull
        Integer matchingIndex,

        @NotNull
        Integer readyIndex
) {}
