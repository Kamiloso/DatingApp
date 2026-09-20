package page.se3.datingapp.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.Transaction;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.onion.accessor.ChatAccessor;
import page.se3.datingapp.onion.accessor.MatchAccessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatAccessor chatAccessor;
    private final MatchAccessor matchAccessor;

    public List<Chat> getList(AuthKey authKey) {
        return chatAccessor.getByIdUser(authKey.id());
    }

    @Transaction.ReadCommitted
    public Chat tryPost() {
        throw new RuntimeException();
    }
}
