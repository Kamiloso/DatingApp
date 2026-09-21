package page.se3.datingapp.old.model.struct.post;

public record MatchPost(
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch,
        Boolean isAppliedByFirst,
        Boolean isAppliedBySecond,
        Long idMatchGroup
) {

    private static final Boolean INIT_IS_APPLIED_BY_FIRST = Boolean.FALSE;
    private static final Boolean INIT_IS_APPLIED_BY_SECOND = Boolean.FALSE;

    public MatchPost(
            Long idUserFirst,
            Long idUserSecond,
            Integer score,
            Boolean isDoubleMatch,
            Long idMatchGroup
    ) {
        this(
                idUserFirst, idUserSecond, score, isDoubleMatch,
                INIT_IS_APPLIED_BY_FIRST, INIT_IS_APPLIED_BY_SECOND,
                idMatchGroup
        );
    }
}
