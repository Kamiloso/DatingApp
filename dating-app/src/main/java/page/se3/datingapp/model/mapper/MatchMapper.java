package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.entity.MatchEntity;
import page.se3.datingapp.model.struct.post.MatchPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper {

    @Mapping(target = "id", ignore = true)
    MatchEntity toEntity(MatchPost matchPost);

    Match toDto(MatchEntity matchEntity);
}
