package page.se3.datingapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import page.se3.datingapp.models.dto.*;
import page.se3.datingapp.models.entities.*;
import page.se3.datingapp.models.enums.Gender;
import page.se3.datingapp.models.enums.Relationship;
import page.se3.datingapp.models.requests.UserRegisterRequest;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User toDto(UserEntity userEntity);

    UserHigher toHigherDto(UserEntity userEntity);
    UserHigher toHigherDto(User user);

    UserLower toLowerDto(UserEntity userEntity);
    UserLower toLowerDto(User user);
    UserLower toLowerDto(UserHigher userHigher);

    default UserEntity toEntity(UserRegisterRequest userRegisterRequest, String email) {
        if (userRegisterRequest == null) {
            return null;
        }
        return new UserEntity(
                null,
                email,
                userRegisterRequest.name(),
                userRegisterRequest.surname(),
                userRegisterRequest.gender(),
                userRegisterRequest.age(),
                userRegisterRequest.fieldOfStudy(),
                "",
                Gender.ANY,
                Relationship.BOTH,
                "",
                false,
                0
        );
    }
}
