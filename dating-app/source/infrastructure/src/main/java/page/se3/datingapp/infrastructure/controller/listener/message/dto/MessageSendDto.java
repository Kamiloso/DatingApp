package page.se3.datingapp.infrastructure.controller.listener.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.infrastructure.controller.Template;

@Schema(description = "A request to send message")
public record MessageSendDto(
        @NotNull
        @Size(max = Template.LEN_2047)
        String message
) {}
