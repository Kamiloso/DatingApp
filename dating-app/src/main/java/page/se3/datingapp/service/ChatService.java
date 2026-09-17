package page.se3.datingapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.service.table.ChatTableService;

import java.util.List;

public interface ChatService {
    List<Chat> getList(AuthKey authKey);
}

@Service
@RequiredArgsConstructor
class ChatServiceImpl implements ChatService {

    private final ChatTableService chatTableService;

    @Override
    public List<Chat> getList(AuthKey authKey) {
        return chatTableService.getByIdUser(authKey.id());
    }
}
