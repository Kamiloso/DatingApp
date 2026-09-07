package page.se3.datingapp.models.enums;

public enum Relationship {
    FRIENDSHIP, ROMANTIC, BOTH;

    public static boolean isMatch(
            Relationship looksFor1,
            Relationship looksFor2
    ) {
        return looksFor1 == looksFor2
                || looksFor1 == Relationship.BOTH
                || looksFor2 == Relationship.BOTH;
    }
}
