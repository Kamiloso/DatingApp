package page.se3.datingapp.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import page.se3.datingapp.models.entities.*;
import page.se3.datingapp.models.dto.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChatMapper {

    Chat toDto(ChatEntity chatEntity);
}
