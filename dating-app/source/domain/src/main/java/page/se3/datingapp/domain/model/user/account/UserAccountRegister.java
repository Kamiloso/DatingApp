package page.se3.datingapp.domain.model.user.account;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

public record UserAccountRegister(
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
        Boolean isReadyForMatching
) {

    public User deriveInitial(String email, int futureMatchingIndex) {
        Integer matchingIndex = isReadyForMatching ? futureMatchingIndex : -1;

        return User.getInitial(
                email, name, surname, gender, age, fieldOfStudy,
                description, targetRelationshipMan,
                targetRelationshipWoman, targetRelationshipOther,
                quizAnswers, matchingIndex,
                false, true
        );
    }
}
