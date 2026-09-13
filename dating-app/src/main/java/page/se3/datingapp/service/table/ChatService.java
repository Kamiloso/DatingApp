package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.mapper.ChatMapper;
import page.se3.datingapp.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> getById(Long id);
    List<Chat> getByIdUser(Long idUser);
}

@Service
@RequiredArgsConstructor
class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public Optional<Chat> getById(Long id) {
        return chatRepository
                .findById(id)
                .map(chatMapper::toDto);
    }

    @Override
    public List<Chat> getByIdUser(Long idUser) {
        return chatRepository
                .findByIdUser(idUser)
                .stream()
                .map(chatMapper::toDto)
                .toList();
    }
}
