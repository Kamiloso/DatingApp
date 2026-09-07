package page.se3.datingapp.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.With;
import page.se3.datingapp.core.Const;

@With
public record MessageSendRequest(
        @NotNull
        Long idChat,

        @NotBlank
        @Size(max = Const.LONG_TEXT_CAPACITY)
        String message
) {}
