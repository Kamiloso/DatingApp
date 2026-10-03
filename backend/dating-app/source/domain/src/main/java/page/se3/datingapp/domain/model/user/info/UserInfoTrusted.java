package page.se3.datingapp.domain.model.user.info;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

public record UserInfoTrusted(
        Long id,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Relationship targetRelationship
) {

    public static UserInfoTrusted getFrom(User user, Gender askingGender) {
        return new UserInfoTrusted(
                user.id(),
                user.name(),
                user.surname(),
                user.gender(),
                user.age(),
                user.fieldOfStudy(),
                user.description(),
                user.targetRelationshipFor(askingGender)
        );
    }
}
