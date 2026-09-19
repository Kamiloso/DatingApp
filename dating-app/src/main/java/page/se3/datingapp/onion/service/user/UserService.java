package page.se3.datingapp.onion.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.mapper.UserDowncastMapper;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.dto.UserHigher;
import page.se3.datingapp.model.struct.dto.UserLower;
import page.se3.datingapp.onion.accessor.ChatAccessor;
import page.se3.datingapp.onion.accessor.UserAccessor;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ChatAccessor chatAccessor;
    private final UserAccessor userAccessor;
    private final UserDowncastMapper userDowncastMapper;

    public List<UserLower> getLowerList(AuthKey authKey) {
        return StreamMap.fromList(
                userAccessor.getByIdList(getPermittedIdUserList(authKey.id(), chat -> true)),
                userDowncastMapper::toLower
        );
    }

    public List<UserHigher> getHigherList(AuthKey authKey) {
        return StreamMap.fromList(
                userAccessor.getByIdList(getPermittedIdUserList(authKey.id(), Chat::isUpgraded)),
                userDowncastMapper::toHigher
        );
    }

    private List<Long> getPermittedIdUserList(Long idUser, Predicate<Chat> filter) {
        return chatAccessor.getByIdUser(idUser)
                .stream()
                .filter(filter)
                .flatMap(chat -> Stream.of(chat.idUserFirst(), chat.idUserSecond()))
                .distinct()
                .toList();
    }
}
