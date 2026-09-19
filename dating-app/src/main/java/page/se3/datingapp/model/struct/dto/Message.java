package page.se3.datingapp.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.infrastructure.util.Final;

import java.time.Instant;

@Schema(description = "Represents a complete message entry")
public record Message(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Final.LEN_2047)
        String message,

        @NotNull
        Instant sendTime,

        @NotNull
        Long idChat,

        @NotNull
        Long idUserSender
) {}
