package page.se3.datingapp.infrastructure.controller.listener.relation.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RelationChatDto(
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
        Instant beginTime,

        @NotNull
        Boolean isUpgraded
) {}
