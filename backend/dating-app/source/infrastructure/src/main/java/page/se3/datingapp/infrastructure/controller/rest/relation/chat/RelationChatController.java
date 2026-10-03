package page.se3.datingapp.infrastructure.controller.rest.relation.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.relation.chat.dto.RelationChatDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Chats")
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationChatController {

    private final RelationChatProxy relationChatProxy;

    @GetMapping("/{idMatch}/chat")
    @Operation(summary = "Gets chat relation")
    public RelationChatDto getChatRelation(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch
    ) throws RelationNotFound {
        return relationChatProxy.getChatRelation(authKey, idMatch);
    }

    @GetMapping("/chat")
    @Operation(summary = "Gets all chat relations")
    public List<RelationChatDto> getChatRelations(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Boolean blocked
    ) {
        return relationChatProxy.getChatRelations(authKey, blocked);
    }
}
