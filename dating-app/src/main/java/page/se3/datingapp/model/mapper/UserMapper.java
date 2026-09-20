package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.dto.UserHigher;
import page.se3.datingapp.model.struct.dto.UserLower;
import page.se3.datingapp.model.struct.entity.UserEntity;
import page.se3.datingapp.model.struct.post.UserPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserPost userPost);

    User toDto(UserEntity userEntity);

    UserHigher toHigher(User user);
    UserLower toLower(User user);
}
