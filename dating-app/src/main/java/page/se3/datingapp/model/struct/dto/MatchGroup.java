package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record MatchGroup(
        @NotNull
        Long id,

        @NotNull
        Instant matchTime,

        @NotNull
        Integer matchCountLimit,

        @NotNull
        Integer readyIndex
) {}
