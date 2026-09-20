package page.se3.datingapp.onion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.model.request.UserAccountRegisterRequest;
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.onion.service.matching.tool.Matching;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PostGetService {

    // TODO: do something with this

    private final AppConfig appConfig;

    public UserPost getUser(UserAccountRegisterRequest userAccountRegisterRequest, String email) {
        return new UserPost(
                email,
                userAccountRegisterRequest.name(),
                userAccountRegisterRequest.surname(),
                userAccountRegisterRequest.gender(),
                userAccountRegisterRequest.age(),
                userAccountRegisterRequest.fieldOfStudy(),
                "",
                Gender.ANY,
                Relationship.BOTH,
                "",
                false,
                0
        );
    }

    public MessagePost getMessage(
            MsgSendRequest msgSendRequest,
            Instant sendTime,
            Long idUserSender,
            Long idChat
    ) {
        return new MessagePost(
                msgSendRequest.message(),
                sendTime,
                idUserSender,
                idChat
        );
    }

    public MatchGroupPost getMatchGroup() {
        return new MatchGroupPost(
                Instant.now(),
                appConfig.matching().maxMatchesPerUser(),
                appConfig.quiz().readyIndex()
        );
    }

    public MatchPost getMatch(Matching matching, Long idMatchGroup) {
        return new MatchPost(
                matching.idUserMain(),
                matching.idUserMatch(),
                matching.score(),
                matching.isDoubleMatch(),
                false,
                false,
                idMatchGroup
        );
    }
}
