package page.se3.datingapp.application.service.user;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.relation.assembler.SingleRelationAssembler;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.info.UserInfoBrief;
import page.se3.datingapp.domain.model.user.info.UserInfoKnown;
import page.se3.datingapp.domain.model.user.info.UserInfoTrusted;
import page.se3.datingapp.application.service.user.repository.UserRepository;
import page.se3.datingapp.domain.model.user.*;

@RootService
@RequiredArgsConstructor
public class UserInfoService {

    private final SingleRelationAssembler singleRelationAssembler;
    private final UserRepository userRepository;

    public UserInfoBrief getBrief(AuthKey authKey, Long idUser) throws RelationNotFound {
        checkRelation(authKey.id(), idUser, 0, true, false);
        return UserInfoBrief.getFrom(getUser(idUser), authKey.gender());
    }

    public UserInfoKnown getKnown(AuthKey authKey, Long idUser) throws RelationNotFound {
        checkRelation(authKey.id(), idUser, 1, true, false);
        return UserInfoKnown.getFrom(getUser(idUser), authKey.gender());
    }

    public UserInfoTrusted getTrusted(AuthKey authKey, Long idUser) throws RelationNotFound {
        checkRelation(authKey.id(), idUser, 1, false, true);
        return UserInfoTrusted.getFrom(getUser(idUser), authKey.gender());
    }

    private void checkRelation(
            Long idUser, Long idUserOther,
            int withChat, boolean blocked, boolean requireUpgraded
    ) throws RelationNotFound {
        singleRelationAssembler.getByIdUserDirectForApplicationPurposes(idUser, idUserOther, withChat, blocked)
                .filter(r -> !requireUpgraded || r.chat().isUpgraded())
                .orElseThrow(RelationNotFound::new);
    }

    private User getUser(Long idUser) throws RelationNotFound {
        return userRepository.findByIdWhereIsActive(idUser)
                .orElseThrow(RelationNotFound::new);
    }
}
