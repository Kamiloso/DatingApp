package page.se3.datingapp.old.model.struct.post;

import java.time.Instant;

public record MatchGroupPost(
        Instant matchTime,
        Integer matchCountLimit,
        Integer readyIndex
) {}
