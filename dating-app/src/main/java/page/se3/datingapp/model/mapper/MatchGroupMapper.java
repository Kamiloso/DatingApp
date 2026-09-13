package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.model.struct.post.MatchGroupPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchGroupMapper {

    @Mapping(target = "id", ignore = true)
    MatchGroupEntity toEntity(MatchGroupPost matchGroupPost);

    MatchGroup toDto(MatchGroupEntity matchGroupEntity);
}
