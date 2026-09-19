package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.mapper.ChatMapper;
import page.se3.datingapp.onion.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatAccessor {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public Optional<Chat> getById(Long id) {
        return chatRepository.findById(id)
                .map(chatMapper::toDto);
    }

    public List<Chat> getByIdUser(Long idUser) {
        return StreamMap.fromList(
                chatRepository.findByIdUser(idUser),
                chatMapper::toDto
        );
    }
}
