package page.se3.datingapp.domain.model.matching.matcher.impl;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.domain.config.MatchingConfig;
import page.se3.datingapp.domain.enums.Relationship;
import page.se3.datingapp.domain.model.matching.matcher.Matcher;
import page.se3.datingapp.domain.model.user.User;

@RequiredArgsConstructor
public class DefaultMatcher implements Matcher {

    private final MatchingConfig matchingConfig;

    @Override
    public int matchCountLimit() {
        return matchingConfig.matchCountLimit();
    }

    @Override
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

    @Override
    public int computeScore(User userMain, User userMatch) {
        return -Math.abs(userMain.age() - userMatch.age()); // TODO: make scoring more robust
    }
}
