package page.se3.datingapp.domain.model.user.account;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.util.Objects;

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
        Boolean isReadyForMatching,
        Boolean isPatched,
        Boolean isActive
) {

    public static UserAccount getFrom(User user, Integer futureMatchingIndex) {
        Boolean isReadyForMatching = Objects.equals(user.matchingIndex(), futureMatchingIndex);

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
                isReadyForMatching,
                user.isPatched(),
                user.isActive()
        );
    }

    public AuthKey getAuthKey() {
        return new AuthKey(id, email, gender);
    }
}
