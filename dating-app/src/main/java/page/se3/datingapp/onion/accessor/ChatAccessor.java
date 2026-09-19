package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.mapper.ChatMapper;
import page.se3.datingapp.onion.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

public interface ChatAccessor {
    Optional<Chat> getById(Long id);
    List<Chat> getByIdUser(Long idUser);
}

@Service
@RequiredArgsConstructor
class ChatAccessorImpl implements ChatAccessor {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public Optional<Chat> getById(Long id) {
        return chatRepository.findById(id)
                .map(chatMapper::toDto);
    }

    @Override
    public List<Chat> getByIdUser(Long idUser) {
        return StreamMap.fromList(
                chatRepository.findByIdUser(idUser),
                chatMapper::toDto
        );
    }
}
