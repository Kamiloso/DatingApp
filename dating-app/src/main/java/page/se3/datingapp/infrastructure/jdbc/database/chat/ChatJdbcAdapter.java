package page.se3.datingapp.infrastructure.jdbc.database.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.repository.ChatRepository;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.infrastructure.jdbc.BatchProcessor;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatJdbcAdapter implements ChatRepository {

    private final BatchProcessor batchProcessor;
    private final ChatJdbcRepository chatJdbcRepository;
    private final ChatMapper chatMapper;

    @Override
    public Optional<Chat> findById(Long id) {
        return chatJdbcRepository.findById(id).map(chatMapper::toDomain);
    }

    @Override
    public Optional<Chat> findByIdMatch(Long id) {
        return chatJdbcRepository.findByIdMatch(id).map(chatMapper::toDomain);
    }

    @Override
    public List<Chat> findAllByIdMatchList(List<Long> idMatchList) {
        return MapUtils.toList(
                batchProcessor.convert(idMatchList, chatJdbcRepository::findAllByIdMatchList),
                chatMapper::toDomain
        );
    }

    @Override
    public void saveIfNotExists(Chat chat) {
        ChatEntity chatEntity = chatMapper.toEntity(chat);
        chatJdbcRepository.saveIfNotExists(chatEntity);
    }
}
