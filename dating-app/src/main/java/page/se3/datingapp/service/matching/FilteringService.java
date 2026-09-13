package page.se3.datingapp.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;

public interface FilteringService {
    boolean meetsMatchRequirements(User userMain, User userMatch);
}

@Service
@RequiredArgsConstructor
class FilteringServiceImpl implements FilteringService {

    private final AppConfig appConfig;

    @Override
    public boolean meetsMatchRequirements(User userMain, User userMatch) {
        AppConfig.Matching matchingConfig = appConfig.matching();

        boolean isAgeMatch = Math.abs(userMain.age() - userMatch.age()) <= matchingConfig.maxAgeDifference();

        boolean isGenderMatch = Gender.isMatch(
                userMain.gender(),
                userMain.targetGender(),
                userMatch.gender(),
                userMatch.targetGender()
        );

        boolean isRelationshipMatch = Relationship.isMatch(
                userMain.targetRelationship(),
                userMatch.targetRelationship()
        );

        return isAgeMatch && isGenderMatch && isRelationshipMatch;
    }
}
