package page.se3.datingapp.infrastructure.controller.listener.relation;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RelationMapper {

    RelationChatDto toDto(RelationChat relationChat);
    RelationMatchDto toDto(RelationMatch relationMatch);
    RelationMatchApplyPatch toDomain(RelationMatchApplyPatchDto relationMatchApplyPatchDto);
}
