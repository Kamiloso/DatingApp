package page.se3.datingapp.old.model.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.model.struct.entity.ChatEntity;
import page.se3.datingapp.old.model.struct.post.ChatPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMapper {

    @Mapping(target = "id", ignore = true)
    ChatEntity toEntity(ChatPost chatPost);

    Chat toDto(ChatEntity chatEntity);
}
