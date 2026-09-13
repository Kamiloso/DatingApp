package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.entity.ChatEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMapper {

    Chat toDto(ChatEntity chatEntity);
}
