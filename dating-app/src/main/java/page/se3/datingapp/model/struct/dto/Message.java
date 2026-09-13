package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.model.Scheme;

import java.time.Instant;

public record Message(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Scheme.LEN_2047)
        String message,

        @NotNull
        Instant sendTime,

        @NotNull
        Long idChat,

        @NotNull
        Long idUserSender
) {}
