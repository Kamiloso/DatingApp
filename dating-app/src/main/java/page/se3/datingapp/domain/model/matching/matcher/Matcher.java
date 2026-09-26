package page.se3.datingapp.domain.model.matching.matcher;

import page.se3.datingapp.domain.model.user.User;

public interface Matcher {

    int matchCountLimit();

    boolean meetsRequirements(User userMain, User userMatch);

    int computeScore(User userMain, User userMatch);
}
