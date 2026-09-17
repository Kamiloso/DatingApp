package page.se3.datingapp.service.msg;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.infrastructure.abort.reason.ChatAccessDenied;
import page.se3.datingapp.infrastructure.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.service.table.ChatTableService;
import page.se3.datingapp.service.table.MessageTableService;

import java.time.Instant;
import java.util.List;

public interface MsgService {
    Message send(AuthKey authKey, Long idChat, MsgSendRequest msgSendRequest)
            throws ChatNotFound, ChatAccessDenied, ReferencedResourceGone;

    DeferredResult<@NonNull List<Message>> fetchMessageList(
            AuthKey authKey,
            Long waitMilliseconds,
            Long idChat,
            Instant lastFetchTime,
            Integer sizeLimit
    ) throws ChatNotFound, ChatAccessDenied;
}

@Service
@RequiredArgsConstructor
class MsgServiceImpl implements MsgService {

    private final ChatTableService chatTableService;
    private final MessageTableService messageTableService;

    @Override
    public Message send(AuthKey authKey, Long idChat, MsgSendRequest msgSendRequest)
            throws ChatNotFound, ChatAccessDenied, ReferencedResourceGone {
        checkExistenceAndPrivileges(authKey, idChat);

        Instant sendTime = Instant.now();
        Long idUserSender = authKey.id();

        MessagePost messagePost = msgSendRequest.upgrade(sendTime, idUserSender, idChat);

        return messageTableService.tryPost(messagePost);
    }

    public DeferredResult<@NonNull List<Message>> fetchMessageList(
            AuthKey authKey,
            Long waitMilliseconds,
            Long idChat,
            Instant lastFetchTime,
            Integer sizeLimit
    ) throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        // TODO: finish
        throw new RuntimeException();
    }

    private void checkExistenceAndPrivileges(AuthKey authKey, Long idChat)
            throws ChatNotFound, ChatAccessDenied {
        Chat chat = chatTableService.getById(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!chat.containsIdUser(authKey.id())) {
            throw new ChatAccessDenied();
        }
    }
}
