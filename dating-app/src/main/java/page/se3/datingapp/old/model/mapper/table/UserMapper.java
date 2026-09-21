package page.se3.datingapp.old.model.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.model.struct.entity.UserEntity;
import page.se3.datingapp.old.model.struct.post.UserPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserPost userPost);

    User toDto(UserEntity userEntity);
}
