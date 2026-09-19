package page.se3.datingapp.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.onion.accessor.ChatAccessor;
import page.se3.datingapp.onion.accessor.MatchAccessor;
import page.se3.datingapp.onion.accessor.MatchGroupAccessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final ChatAccessor chatAccessor;
    private final MatchAccessor matchAccessor;
    private final MatchGroupAccessor matchGroupAccessor;

    public List<Chat> getChatList(AuthKey authKey) {
        return chatAccessor.getByIdUser(authKey.id());
    }
}
