package page.se3.datingapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.models.dto.Chat;
import page.se3.datingapp.models.dto.User;
import page.se3.datingapp.models.mappers.ChatMapper;
import page.se3.datingapp.repositories.ChatRepository;

import java.util.List;

public interface ChatService {
    List<Chat> getByUser(User user);
}

@Service
@RequiredArgsConstructor
class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public List<Chat> getByUser(User user) {
        return chatRepository
                .findByIdUser(user.id())
                .stream()
                .map(chatMapper::toDto)
                .toList();
    }
}
