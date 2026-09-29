package page.se3.datingapp.infrastructure.controller.listener.relation.dto;

import jakarta.validation.constraints.NotNull;

public record RelationMatchApplyPatchDto(
        @NotNull
        Boolean isAppliedByYou
) {}
