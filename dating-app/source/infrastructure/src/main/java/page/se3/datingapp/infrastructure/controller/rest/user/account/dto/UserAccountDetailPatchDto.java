package page.se3.datingapp.infrastructure.controller.rest.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.Final;

@Schema(description = "A request to patch basic user account information")
public record UserAccountDetailPatchDto(
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
        Boolean isReadyForMatching,

        @NotNull
        Boolean isActive
) {}
