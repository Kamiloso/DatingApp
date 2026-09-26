package page.se3.datingapp.infrastructure.rest.controller.relation;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.application.service.relation.struct.RelationApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.domain.model.relation.apply.RelationApply;
import page.se3.datingapp.infrastructure.rest.controller.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.rest.controller.relation.dto.RelationApplyDto;
import page.se3.datingapp.infrastructure.rest.controller.relation.dto.RelationApplyPatchDto;
import page.se3.datingapp.infrastructure.rest.controller.relation.dto.RelationMatchDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RelationMapper {

    RelationChatDto toDto(RelationChat relationChat);
    RelationMatchDto toDto(RelationMatch relationMatch);
    RelationApplyDto toDto(RelationApply relationApply);
    RelationApplyPatch toDomain(RelationApplyPatchDto relationApplyPatchDto);
}
