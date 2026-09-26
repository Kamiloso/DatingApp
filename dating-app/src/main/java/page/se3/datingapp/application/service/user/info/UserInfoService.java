package page.se3.datingapp.application.service.user.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.RootService;
import page.se3.datingapp.application.service.relation.assembly.MultiRelationAssembler;
import page.se3.datingapp.domain.model.user.info.UserBriefInfo;
import page.se3.datingapp.domain.model.user.info.UserKnownInfo;
import page.se3.datingapp.domain.model.user.info.UserTrustedInfo;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.application.repository.UserRepository;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.user.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

@Service
@RootService
@RequiredArgsConstructor
public class UserInfoService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final UserRepository userRepository;

    public List<UserBriefInfo> getBriefList(UserKey userKey) {
        List<Relation> briefRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(userKey.id(), 0, true);

        return getUserInfoList(userKey, briefRelations, UserBriefInfo::getFrom);
    }

    public List<UserKnownInfo> getKnownList(UserKey userKey) {
        List<Relation> knownRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(userKey.id(), 1, true);

        return getUserInfoList(userKey, knownRelations, UserKnownInfo::getFrom);
    }

    public List<UserTrustedInfo> getTrustedList(UserKey userKey) {
        List<Relation> trustedRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(userKey.id(), 1, false).stream()
                .filter(r -> r.chat().isUpgraded())
                .toList();

        return getUserInfoList(userKey, trustedRelations, UserTrustedInfo::getFrom);
    }

    private <R> List<R> getUserInfoList(
            UserKey userKey,
            List<Relation> relationList,
            BiFunction<User, Gender, R> mapper
    ) {
        Set<Long> idList = new HashSet<>();

        idList.add(userKey.id());
        relationList.forEach(r -> idList.add(r.match().idUserFirst()));
        relationList.forEach(r -> idList.add(r.match().idUserSecond()));

        return MapUtils.toList(
                userRepository.findAllByIdList(List.copyOf(idList)),
                u -> mapper.apply(u, userKey.gender())
        );
    }
}
