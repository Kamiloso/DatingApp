package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.entity.MessageEntity;
import page.se3.datingapp.model.struct.post.MessagePost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    MessageEntity toEntity(MessagePost messagePost);

    Message toDto(MessageEntity messageEntity);
}
