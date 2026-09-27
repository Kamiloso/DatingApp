package page.se3.datingapp.infrastructure.controller.listener.relation.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RelationMatchDto(
        @NotNull
        Long idMatch,

        @NotNull
        Long idUserFirst,

        @NotNull
        Long idUserSecond,

        @NotNull
        Instant matchTime,

        @NotNull
        Integer score,

        @NotNull
        Boolean isDoubleMatch,

        @NotNull
        Boolean isAppliedByFirst,

        @NotNull
        Boolean isAppliedBySecond,

        @NotNull
        Boolean chatExists
) {}
