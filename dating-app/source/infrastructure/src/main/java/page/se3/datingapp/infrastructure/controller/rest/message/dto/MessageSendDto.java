package page.se3.datingapp.infrastructure.controller.rest.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.Final;

@Schema(description = "A request to send message")
public record MessageSendDto(
        @NotNull
        @Size(max = Final.LEN_2047)
        String message
) {}
