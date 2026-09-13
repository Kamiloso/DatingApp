package page.se3.datingapp.model.struct.post;

import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;

public record UserPost(
        String email,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Gender targetGender,
        Relationship targetRelationship,
        String quizAnswers,
        Boolean isParticipating,
        Integer readyIndex
) {

    private static final String DEFAULT_DESCRIPTION = "";
    private static final Gender DEFAULT_TARGET_GENDER = Gender.ANY;
    private static final Relationship DEFAULT_TARGET_RELATIONSHIP = Relationship.BOTH;
    private static final String DEFAULT_QUIZ_ANSWERS = "";
    private static final Boolean DEFAULT_IS_PARTICIPATING = false;
    private static final Integer DEFAULT_READY_INDEX = 0;

    public static UserPost createInitial(
            String email, String name, String surname,
            Gender gender, Integer age, String fieldOfStudy
    ) {
        return new UserPost(
                email, name, surname, gender, age, fieldOfStudy,
                DEFAULT_DESCRIPTION, DEFAULT_TARGET_GENDER, DEFAULT_TARGET_RELATIONSHIP,
                DEFAULT_QUIZ_ANSWERS, DEFAULT_IS_PARTICIPATING, DEFAULT_READY_INDEX
        );
    }
}
