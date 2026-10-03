package page.se3.datingapp.infrastructure.controller.rest.relation.chat;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.infrastructure.controller.rest.relation.chat.dto.RelationChatDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RelationChatMapper {

    RelationChatDto toDto(RelationChat relationChat);
}
