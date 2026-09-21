package page.se3.datingapp.old.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.model.struct.dto.table.MatchGroup;

@Schema(description = "Represents the match entry with additional connected data")
public record MatchReport(
        @NotNull
        @Valid
        Match match,

        @NotNull
        @Valid
        MatchGroup matchGroup,

        @Valid
        Chat chat
) {}
