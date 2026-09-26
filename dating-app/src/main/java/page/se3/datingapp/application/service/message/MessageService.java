package page.se3.datingapp.application.service.message;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.message.channel.MessageChannelManager;
import page.se3.datingapp.application.service.message.struct.MessageSend;
import page.se3.datingapp.application.service.relation.assembly.SingleRelationAssembler;
import page.se3.datingapp.domain.exception.abort.reason.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.ChatNotFound;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.application.service.message.struct.MessagesFetched;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@RootService
@RequiredArgsConstructor
public class MessageService {

    private final SingleRelationAssembler singleRelationAssembler;
    private final MessageChannelManager messageChannelManager;

    public Message send(UserKey userKey, Long idChat, MessageSend messageSend)
            throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(userKey, idChat);

        try {
            return messageChannelManager.send(userKey, idChat, messageSend);
        } catch (DataIntegrityViolation e) {
            throw new ChatNotFound();
        }
    }

    public CompletableFuture<MessagesFetched> fetch(
            UserKey userKey,
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(userKey, idChat);

        return messageChannelManager.fetch(idChat, lastFetchTime, maxListSize, waitTimeout);
    }

    private void checkExistenceAndPrivileges(UserKey userKey, Long idChat)
            throws ChatNotFound, ChatAccessDenied {
        Relation relation = singleRelationAssembler.getByIdChat(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!relation.chatExists()) {
            throw new ChatNotFound();
        }

        if (relation.match().containsIdUser(userKey.id())) {
            throw new ChatAccessDenied();
        }
    }
}
