package page.se3.datingapp.infrastructure.rest.controller.relation.dto;

import jakarta.validation.constraints.NotNull;

public record RelationApplyDto(
        @NotNull
        Boolean isAppliedByYou,

        @NotNull
        Boolean isAppliedByOther,

        @NotNull
        Boolean chatExists
) {}
