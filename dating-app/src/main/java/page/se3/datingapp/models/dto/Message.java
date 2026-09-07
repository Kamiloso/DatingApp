package page.se3.datingapp.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.With;
import page.se3.datingapp.core.Const;

import java.time.Instant;

@With
public record Message(
        @NotNull
        Long id,

        @NotBlank
        @Size(max = Const.LONG_TEXT_CAPACITY)
        String message,

        @NotNull
        Instant sendTime,

        @NotNull
        Long idChat,

        @NotNull
        Long idUserSender
) {}
