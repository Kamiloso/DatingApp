package page.se3.datingapp.domain.service.matcher;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.domain.annotation.DomainService;
import page.se3.datingapp.domain.config.app.matching.MatchingConfig;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.user.User;

@DomainService
@RequiredArgsConstructor
public class UserMatcher {

    private final MatchingConfig matchingConfig;

    public int matchCountLimit() {
        return matchingConfig.matchCountLimit();
    }

    public boolean meetsRequirements(User userMain, User userMatch) {
        int maxAgeDifference = matchingConfig.maxAgeDifference();
        int ageDifference = Math.abs(userMain.age() - userMatch.age());

        boolean isAgeMatch = ageDifference <= maxAgeDifference;

        boolean isRelationshipMatch = Relationship.areCompatible(
                userMain.targetRelationshipFor(userMatch.gender()),
                userMatch.targetRelationshipFor(userMain.gender())
        );

        return isAgeMatch && isRelationshipMatch;
    }

    public int computeScore(User userMain, User userMatch) {
        return -Math.abs(userMain.age() - userMatch.age()); // TODO: make scoring more robust
    }
}
