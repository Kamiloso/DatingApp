package page.se3.datingapp.domain.enums;

public enum Relationship {
    NONE, FRIENDSHIP, ROMANTIC, BOTH;

    public static boolean areCompatible(Relationship first, Relationship second) {
        return first != NONE && second != NONE && (first == second || first == BOTH || second == BOTH);
    }
}
