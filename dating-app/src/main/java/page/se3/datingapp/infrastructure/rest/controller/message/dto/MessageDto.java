package page.se3.datingapp.infrastructure.rest.controller.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.infrastructure.rest.Template;

import java.time.Instant;

@Schema(description = "Represents a single message")
public record MessageDto(
        @NotNull
        Long id,

        @Size(max = Template.LEN_2047)
        String message,

        @NotNull
        Instant sendTime,

        @NotNull
        Long idChat,

        @NotNull
        Long idUserSender
) {}
