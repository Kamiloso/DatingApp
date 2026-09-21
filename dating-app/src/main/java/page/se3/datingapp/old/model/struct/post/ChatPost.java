package page.se3.datingapp.old.model.struct.post;

import java.time.Instant;

public record ChatPost(
        Long idUserFirst,
        Long idUserSecond,
        Instant beginTime,
        Boolean isUpgraded
) {}
