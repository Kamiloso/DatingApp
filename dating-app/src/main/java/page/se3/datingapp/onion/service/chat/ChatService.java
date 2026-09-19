package page.se3.datingapp.onion.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.onion.accessor.ChatAccessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatAccessor chatAccessor;

    public List<Chat> getList(AuthKey authKey) {
        return chatAccessor.getByIdUser(authKey.id());
    }
}
