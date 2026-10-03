package page.se3.datingapp.domain.model.user.account;

import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

public record UserAccountDetailPatch(
        String fieldOfStudy,
        String description,
        Relationship targetRelationshipMan,
        Relationship targetRelationshipWoman,
        Relationship targetRelationshipOther,
        String quizAnswers,
        Boolean isReadyForMatching,
        Boolean isActive
) {

    public User applyPatch(User user, int futureMatchingIndex) {
        Integer matchingIndex = isReadyForMatching ? futureMatchingIndex : -1;

        return user.toBuilder()
                .fieldOfStudy(fieldOfStudy)
                .description(description)
                .targetRelationshipMan(targetRelationshipMan)
                .targetRelationshipWoman(targetRelationshipWoman)
                .targetRelationshipOther(targetRelationshipOther)
                .quizAnswers(quizAnswers)
                .matchingIndex(matchingIndex)
                .isActive(isActive)
                .build();
    }
}
