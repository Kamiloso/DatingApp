package page.se3.datingapp.domain.model.user;

import page.se3.datingapp.domain.annotation.MayBeNull;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;

public record User(
        @MayBeNull Long id,
        String email,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Relationship targetRelationshipMan,
        Relationship targetRelationshipWoman,
        Relationship targetRelationshipOther,
        String quizAnswers,
        Integer matchingIndex,
        Integer readyIndex
) {

    public static User getInitial(
            String email, String name, String surname, Gender gender, Integer age, String fieldOfStudy,
            String description, Relationship targetRelationshipMan,
            Relationship targetRelationshipWoman, Relationship targetRelationshipOther,
            String quizAnswers, Integer matchingIndex, Integer readyIndex
    ) {
        return new User(
                null, email, name, surname, gender, age, fieldOfStudy,
                description, targetRelationshipMan,
                targetRelationshipWoman, targetRelationshipOther,
                quizAnswers, matchingIndex, readyIndex
        );
    }

    public Relationship targetRelationshipFor(Gender gender) {
        return switch (gender) {
            case Gender.MAN -> targetRelationshipMan;
            case Gender.WOMAN -> targetRelationshipWoman;
            case Gender.OTHER -> targetRelationshipOther;
        };
    }
}
