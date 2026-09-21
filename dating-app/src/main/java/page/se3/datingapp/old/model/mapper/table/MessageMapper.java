package page.se3.datingapp.old.model.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.old.model.struct.dto.table.Message;
import page.se3.datingapp.old.model.struct.entity.MessageEntity;
import page.se3.datingapp.old.model.struct.post.MessagePost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    MessageEntity toEntity(MessagePost messagePost);

    Message toDto(MessageEntity messageEntity);
}
