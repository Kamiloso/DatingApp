package page.se3.datingapp.domain.model.user.account;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.model.user.UserKey;

public record UserAccount(
        Long id,
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

    public static UserAccount getFrom(User user) {
        return new UserAccount(
                user.id(),
                user.email(),
                user.name(),
                user.surname(),
                user.gender(),
                user.age(),
                user.fieldOfStudy(),
                user.description(),
                user.targetRelationshipMan(),
                user.targetRelationshipWoman(),
                user.targetRelationshipOther(),
                user.quizAnswers(),
                user.matchingIndex(),
                user.readyIndex()
        );
    }

    public UserKey getUserKey() {
        return new UserKey(id, gender);
    }
}
