package page.se3.datingapp.old.model.struct.post;

import page.se3.datingapp.old.model.enums.Gender;
import page.se3.datingapp.old.model.enums.Relationship;

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

    private static final String INIT_DESCRIPTION = "";
    private static final Gender INIT_TARGET_GENDER = Gender.ANY;
    private static final Relationship INIT_TARGET_RELATIONSHIP = Relationship.BOTH;
    private static final String INIT_QUIZ_ANSWERS = "";
    private static final Boolean INIT_IS_PARTICIPATING = Boolean.FALSE;
    private static final Integer INIT_READY_INDEX = 0;

    public UserPost(String email, String name, String surname, Gender gender, Integer age, String fieldOfStudy) {
        this(
                email, name, surname, gender, age, fieldOfStudy,
                INIT_DESCRIPTION, INIT_TARGET_GENDER, INIT_TARGET_RELATIONSHIP,
                INIT_QUIZ_ANSWERS, INIT_IS_PARTICIPATING, INIT_READY_INDEX
        );
    }
}