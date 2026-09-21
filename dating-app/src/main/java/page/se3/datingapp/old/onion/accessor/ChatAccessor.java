package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.model.mapper.table.ChatMapper;
import page.se3.datingapp.old.model.struct.entity.ChatEntity;
import page.se3.datingapp.old.model.struct.post.ChatPost;
import page.se3.datingapp.old.onion.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatAccessor {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public Optional<Chat> getById(Long id) {
        return chatRepository.findById(id).map(chatMapper::toDto);
    }

    public Optional<Chat> getByIdUserPair(Long idUser1, Long idUser2) {
        return chatRepository.findByIdUserPair(idUser1, idUser2).map(chatMapper::toDto);
    }

    public List<Chat> getByIdUser(Long idUser) {
        return MapUtils.toList(
                chatRepository.findByIdUser(idUser),
                chatMapper::toDto
        );
    }

    public List<Chat> getByIdUserWhereIsUpgraded(Long idUser) {
        return MapUtils.toList(
                chatRepository.findByIdUserWhereIsUpgraded(idUser),
                chatMapper::toDto
        );
    }

    public Chat tryPost(ChatPost chatPost) throws DataIntegrityViolation {
        ChatEntity chatEntity = chatMapper.toEntity(chatPost);

        try {
            chatEntity = chatRepository.save(chatEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return chatMapper.toDto(chatEntity);
    }
}
