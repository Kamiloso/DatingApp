package page.se3.datingapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.ChatAccessDenied;
import page.se3.datingapp.infrastructure.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.service.table.ChatService;
import page.se3.datingapp.service.table.MessageService;

import java.time.Instant;
import java.util.List;

public interface MsgService {
    Message send(MsgSendRequest msgSendRequest, Long idUser)
            throws ChatNotFound, ChatAccessDenied, ReferencedResourceGone;

    List<Message> fetch(Long idChat, Instant lastFetchTime, Long idUser)
            throws ChatNotFound, ChatAccessDenied;
}

@Service
@RequiredArgsConstructor
class MsgServiceImpl implements MsgService {

    private final ChatService chatService;
    private final MessageService messageService;

    @Override
    public Message send(MsgSendRequest msgSendRequest, Long idUser)
            throws ChatNotFound, ChatAccessDenied, ReferencedResourceGone {
        Long idChat = msgSendRequest.idChat();

        checkExistenceAndPrivileges(idChat, idUser);

        Instant sendTime = Instant.now();
        MessagePost messagePost = msgSendRequest.upgrade(sendTime, idUser);

        return messageService.tryPost(messagePost);
    }

    @Override
    public List<Message> fetch(Long idChat, Instant lastFetchTime, Long idUser)
            throws ChatNotFound, ChatAccessDenied {
        checkExistenceAndPrivileges(idChat, idUser);

        return messageService.getNextMessageListByIdChat(idChat, lastFetchTime);
    }

    private void checkExistenceAndPrivileges(Long idChat, Long idUser)
            throws ChatNotFound, ChatAccessDenied {
        Chat chat = chatService
                .getById(idChat)
                .orElseThrow(ChatNotFound::new);

        if (!chat.containsIdUser(idUser)) {
            throw new ChatAccessDenied();
        }
    }
}
