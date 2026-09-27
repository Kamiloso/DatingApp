package page.se3.datingapp.infrastructure.database.jdbc.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends RoundTripEntityMapper<User, UserEntity> {}
