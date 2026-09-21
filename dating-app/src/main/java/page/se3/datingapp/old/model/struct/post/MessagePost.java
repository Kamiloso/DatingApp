package page.se3.datingapp.old.model.struct.post;

import java.time.Instant;

public record MessagePost(
        String message,
        Instant sendTime,
        Long idUserSender,
        Long idChat
) {}
