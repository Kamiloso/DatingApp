package page.se3.datingapp.infrastructure.database.jdbc.chat;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMapper extends RoundTripEntityMapper<Chat, ChatEntity> {}
