package page.se3.datingapp.infrastructure.jdbc.database.match;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.infrastructure.jdbc.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper extends RoundTripEntityMapper<Match, MatchEntity> {}
