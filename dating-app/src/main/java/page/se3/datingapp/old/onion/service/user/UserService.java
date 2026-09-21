package page.se3.datingapp.old.onion.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.model.mapper.UserDtoMapper;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.UserBrief;
import page.se3.datingapp.old.model.struct.dto.UserKnown;
import page.se3.datingapp.old.model.struct.dto.UserTrusted;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.onion.accessor.ChatAccessor;
import page.se3.datingapp.old.onion.accessor.MatchAccessor;
import page.se3.datingapp.old.onion.accessor.UserAccessor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ChatAccessor chatAccessor;
    private final MatchAccessor matchAccessor;
    private final UserAccessor userAccessor;
    private final UserDtoMapper userDtoMapper;

    public List<UserBrief> getBriefList(AuthKey authKey) {
        return getUserList(
                authKey,
                matchAccessor.getByIdUser(authKey.id()),
                Match::idUserFirst,
                Match::idUserSecond,
                userDtoMapper::toBrief
        );
    }

    public List<UserKnown> getKnownList(AuthKey authKey) {
        return getUserList(
                authKey,
                chatAccessor.getByIdUser(authKey.id()),
                Chat::idUserFirst,
                Chat::idUserSecond,
                userDtoMapper::toKnown
        );
    }

    public List<UserTrusted> getTrustedList(AuthKey authKey) {
        return getUserList(
                authKey,
                chatAccessor.getByIdUserWhereIsUpgraded(authKey.id()),
                Chat::idUserFirst,
                Chat::idUserSecond,
                userDtoMapper::toTrusted
        );
    }

    private <T, R> List<R> getUserList(
            AuthKey authKey,
            List<T> relationList,
            Function<T, Long> getIdFirst,
            Function<T, Long> getIdSecond,
            Function<User, R> mapper
    ) {
        List<Long> idList = Stream.concat(
                Stream.of(authKey.id()),
                relationList.stream().flatMap(r -> Stream.of(getIdFirst.apply(r), getIdSecond.apply(r)))
        ).distinct().toList();

        return MapUtils.toList(userAccessor.getByIdList(idList), mapper);
    }
}
