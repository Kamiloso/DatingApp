package page.se3.datingapp.infrastructure.controller.rest.user.info.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.Final;

@Schema(description = "Represents brief user info (permitted by match)")
public record UserInfoBriefDto(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Final.LEN_255)
        String name,

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
        Relationship targetRelationship
) {}
