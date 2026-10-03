package page.se3.datingapp.application.service.message;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.message.repository.MessageRepository;
import page.se3.datingapp.domain.exception.abort.reason.message.MessageNotFound;
import page.se3.datingapp.domain.model.message.MessageSend;
import page.se3.datingapp.application.service.relation.assembler.SingleRelationAssembler;
import page.se3.datingapp.domain.config.app.limiting.capacity.CapacityConfig;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class MessageService {

    private final CapacityConfig capacityConfig;
    private final SingleRelationAssembler singleRelationAssembler;
    private final MessageRepository messageRepository;

    public Message send(AuthKey authKey, Long idChat, MessageSend messageSend)
            throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        Message message = messageSend.deriveInitial(Instant.now(), idChat, authKey.id());

        try {
            return messageRepository.trySave(message);
        } catch (DatabaseConflictException e) {
            throw new ChatNotFound();
        }
    }

    public Message get(AuthKey authKey, Long idChat, Long idMessage)
            throws ChatNotFound, ChatAccessDenied, MessageNotFound {
        checkExistenceAndPrivileges(authKey, idChat);

        return messageRepository.findByIdAndIdChat(idMessage, idChat)
                .orElseThrow(MessageNotFound::new);
    }

    public List<Message> fetch(
            AuthKey authKey,
            Long idChat,
            Instant lastFetchTime
    ) throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        Integer maxListSize = capacityConfig.maxListSize();

        return messageRepository.findAllByIdChatForApplicationPurposes(idChat, lastFetchTime, maxListSize);
    }

    private void checkExistenceAndPrivileges(AuthKey authKey, Long idChat)
            throws ChatNotFound, ChatAccessDenied {
        Relation relation = singleRelationAssembler.getByIdChat(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!relation.match().containsIdUser(authKey.id())) {
            throw new ChatAccessDenied();
        }
    }
}
