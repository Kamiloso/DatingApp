package page.se3.datingapp.application.service.user.account.struct;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

public record UserRegisterRequest(
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
        Integer readyIndex
) {

    public User deriveInitial(String email) {
        return User.getInitial(
                email, name, surname, gender, age, fieldOfStudy,
                description, targetRelationshipMan,
                targetRelationshipWoman, targetRelationshipOther,
                quizAnswers, 0, readyIndex
        );
    }
}
