package page.se3.datingapp.application.service.message;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.repository.MessageRepository;
import page.se3.datingapp.application.service.message.struct.MessageSend;
import page.se3.datingapp.application.service.relation.assembly.SingleRelationAssembler;
import page.se3.datingapp.domain.config.LimitConfig;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class MessageService {

    private final LimitConfig limitConfig;
    private final SingleRelationAssembler singleRelationAssembler;
    private final MessageRepository messageRepository;

    public Message send(AuthKey authKey, Long idChat, MessageSend messageSend)
            throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        var sendTime = Instant.now();
        Long idUserSender = authKey.id();

        Message message = messageSend.deriveInitial(sendTime, idChat, idUserSender);

        try {
            return messageRepository.trySave(message);
        } catch (DataIntegrityViolation e) {
            throw new ChatNotFound();
        }
    }

    public List<Message> fetch(
            AuthKey authKey,
            Long idChat,
            Instant lastFetchTime
    ) throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(authKey, idChat);

        Integer maxListSize = limitConfig.maxListSize();

        return messageRepository.findAllByIdChatForApplicationPurposes(idChat, lastFetchTime, maxListSize);
    }

    private void checkExistenceAndPrivileges(AuthKey authKey, Long idChat)
            throws ChatNotFound, ChatAccessDenied {
        Relation relation = singleRelationAssembler.getByIdChat(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!relation.chatExists()) {
            throw new ChatNotFound();
        }

        if (relation.match().containsIdUser(authKey.id())) {
            throw new ChatAccessDenied();
        }
    }
}
