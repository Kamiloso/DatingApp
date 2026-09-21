package page.se3.datingapp.old.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import page.se3.datingapp.old.model.struct.dto.UserBrief;
import page.se3.datingapp.old.model.struct.dto.UserKnown;
import page.se3.datingapp.old.model.struct.dto.UserTrusted;
import page.se3.datingapp.old.model.struct.dto.table.User;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserDtoMapper {

    UserKnown toKnown(User user);
    UserBrief toBrief(User user);
    UserTrusted toTrusted(User user);
}
