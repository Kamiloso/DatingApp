package page.se3.datingapp.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;

@Service
@RequiredArgsConstructor
public class FilteringService {

    private final AppConfig appConfig;

    public boolean meetsMatchRequirements(User userMain, User userMatch) {
        int maxAgeDifference = appConfig.matching().maxAgeDifference();

        boolean isAgeMatch = Math.abs(userMain.age() - userMatch.age()) <= maxAgeDifference;

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
