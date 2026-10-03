package page.se3.datingapp.infrastructure.controller.rest.relation.match;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.match.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RelationMatchMapper {

    RelationMatchDto toDto(RelationMatch relationMatch);
    RelationMatchApplyPatch toDomain(RelationMatchApplyPatchDto relationMatchApplyPatchDto);
}
