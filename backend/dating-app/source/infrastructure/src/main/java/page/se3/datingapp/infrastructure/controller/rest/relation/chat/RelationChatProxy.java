package page.se3.datingapp.infrastructure.controller.rest.relation.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.relation.RelationChatService;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.rest.relation.chat.dto.RelationChatDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class RelationChatProxy {

    private final RelationChatService relationChatService;
    private final RelationChatMapper relationChatMapper;

    public List<RelationChatDto> getChatRelations(AuthKey authKey, boolean blocked) {
        return Streams.toList(
                relationChatService.getChatList(authKey, blocked),
                relationChatMapper::toDto
        );
    }

    public RelationChatDto getChatRelation(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return Streams.map(
                relationChatService.getChat(authKey, idMatch),
                relationChatMapper::toDto
        );
    }
}
