package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.dto.UserHigher;
import page.se3.datingapp.model.struct.dto.UserLower;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserDowncastMapper {

    UserHigher toHigher(User user);

    UserLower toLower(User user);
    UserLower toLower(UserHigher userHigher);
}
