package page.se3.datingapp.infrastructure.controller.listener.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.infrastructure.controller.Template;

@Schema(description = "A request to patch basic user account information")
public record UserAccountDetailPatchDto(
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
        Boolean isReadyForMatching,

        @NotNull
        Integer readyIndex,

        @NotNull
        Boolean isActive
) {}
