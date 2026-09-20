package page.se3.datingapp.model.struct.post;

public record MatchPost(
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch,
        Boolean isAppliedByFirst,
        Boolean isAppliedBySecond,
        Long idMatchGroup
) {}
