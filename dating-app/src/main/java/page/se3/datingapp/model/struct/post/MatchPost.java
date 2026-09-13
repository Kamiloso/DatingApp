package page.se3.datingapp.model.struct.post;

public record MatchPost(
        Long idUserMain,
        Long idUserMatch,
        Integer score,
        Long idMatchGroup
) {}
