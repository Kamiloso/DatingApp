package page.se3.datingapp.infrastructure.database.jdbc.matchgroup;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchGroupMapper extends RoundTripEntityMapper<MatchGroup, MatchGroupEntity> {}
