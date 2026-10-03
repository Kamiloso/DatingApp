package page.se3.datingapp.infrastructure.controller.rest.relation.match.dto;

import jakarta.validation.constraints.NotNull;

public record RelationMatchApplyPatchDto(
        @NotNull
        Boolean isAppliedByYou
) {}
