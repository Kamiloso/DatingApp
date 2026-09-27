package page.se3.datingapp.domain.model.message;

import page.se3.datingapp.domain.annotation.MayBeNull;

import java.time.Instant;

public record Message(
        @MayBeNull Long id,
        String message,
        Instant sendTime,
        Long idChat,
        Long idUserSender
) {

    public static Message getInitial(String message, Instant sendTime, Long idChat, Long idUserSender) {
        return new Message(null, message, sendTime, idChat, idUserSender);
    }
}
