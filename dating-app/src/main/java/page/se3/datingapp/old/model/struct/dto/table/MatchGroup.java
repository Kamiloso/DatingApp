package page.se3.datingapp.old.model.struct.dto.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Schema(description = "Represents a complete match group entry")
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
