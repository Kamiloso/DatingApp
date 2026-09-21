package page.se3.datingapp.old.model.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.old.model.struct.dto.table.MatchGroup;
import page.se3.datingapp.old.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.old.model.struct.post.MatchGroupPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchGroupMapper {

    @Mapping(target = "id", ignore = true)
    MatchGroupEntity toEntity(MatchGroupPost matchGroupPost);

    MatchGroup toDto(MatchGroupEntity matchGroupEntity);
}
