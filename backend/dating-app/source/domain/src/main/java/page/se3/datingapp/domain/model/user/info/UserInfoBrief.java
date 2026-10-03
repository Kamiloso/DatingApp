package page.se3.datingapp.domain.model.user.info;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

public record UserInfoBrief(
        Long id,
        String name,
        Gender gender,
        Integer age,
        String fieldOfStudy,
        String description,
        Relationship targetRelationship
) {

    public static UserInfoBrief getFrom(User user, Gender askingGender) {
        return new UserInfoBrief(
                user.id(),
                user.name(),
                user.gender(),
                user.age(),
                user.fieldOfStudy(),
                user.description(),
                user.targetRelationshipFor(askingGender)
        );
    }
}
