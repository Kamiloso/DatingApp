package page.se3.datingapp.old.model.enums;

public enum Gender {
    MAN, WOMAN, OTHER, ANY;

    public static boolean isMatch(
            Gender userGender1,
            Gender expectedGender1,
            Gender userGender2,
            Gender expectedGender2
    ) {
        return userGender1 != Gender.ANY && userGender2 != Gender.ANY
                && (expectedGender1 == Gender.ANY || expectedGender1 == userGender2)
                && (expectedGender2 == Gender.ANY || expectedGender2 == userGender1);
    }
}
