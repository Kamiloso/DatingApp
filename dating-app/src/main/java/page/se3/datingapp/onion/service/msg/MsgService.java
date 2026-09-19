package page.se3.datingapp.onion.service.msg;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.infrastructure.abort.reason.ChatAccessDenied;
import page.se3.datingapp.infrastructure.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.onion.accessor.ChatAccessor;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MsgService {

    private final ChatAccessor chatAccessor;
    private final MsgRealTimeService msgRealTimeService;

    public Message send(AuthKey authKey, Long idChat, MsgSendRequest msgSendRequest)
            throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        MessagePost messagePost = msgSendRequest.upgrade(Instant.now(), authKey.id(), idChat);

        try {
            return msgRealTimeService.sendMessage(messagePost);
        } catch (DataIntegrityViolation e) {
            throw new ChatNotFound();
        }
    }

    public DeferredResult<@NonNull List<Message>> fetchMessageList(
            AuthKey authKey,
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        return msgRealTimeService.fetchMessageListByIdChat(idChat, lastFetchTime, maxListSize, waitTimeout);
    }

    private void checkExistenceAndPrivileges(AuthKey authKey, Long idChat)
            throws ChatNotFound, ChatAccessDenied {
        Chat chat = chatAccessor.getById(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!chat.containsIdUser(authKey.id())) {
            throw new ChatAccessDenied();
        }
    }
}
