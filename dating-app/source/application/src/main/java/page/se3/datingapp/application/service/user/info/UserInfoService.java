package page.se3.datingapp.application.service.user.info;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
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

@RootService
@RequiredArgsConstructor
public class UserInfoService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final UserRepository userRepository;

    public List<UserBriefInfo> getBriefList(AuthKey authKey) {
        List<Relation> briefRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(authKey.id(), 0, true);

        return getUserInfoList(authKey, briefRelations, UserBriefInfo::getFrom);
    }

    public List<UserKnownInfo> getKnownList(AuthKey authKey) {
        List<Relation> knownRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(authKey.id(), 1, true);

        return getUserInfoList(authKey, knownRelations, UserKnownInfo::getFrom);
    }

    public List<UserTrustedInfo> getTrustedList(AuthKey authKey) {
        List<Relation> trustedRelations = multiRelationAssembler
                .getByIdUserForApplicationPurposes(authKey.id(), 1, false).stream()
                .filter(r -> r.chat().isUpgraded())
                .toList();

        return getUserInfoList(authKey, trustedRelations, UserTrustedInfo::getFrom);
    }

    private <R> List<R> getUserInfoList(
            AuthKey authKey,
            List<Relation> relationList,
            BiFunction<User, Gender, R> mapper
    ) {
        Set<Long> idList = new HashSet<>();

        idList.add(authKey.id());
        relationList.forEach(r -> idList.add(r.match().idUserFirst()));
        relationList.forEach(r -> idList.add(r.match().idUserSecond()));

        return MapUtils.toList(
                userRepository.findAllByIdList(List.copyOf(idList)),
                u -> mapper.apply(u, authKey.gender())
        );
    }
}
