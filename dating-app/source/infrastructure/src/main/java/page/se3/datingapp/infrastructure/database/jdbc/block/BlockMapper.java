package page.se3.datingapp.infrastructure.database.jdbc.block;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.infrastructure.database.RoundTripEntityMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper extends RoundTripEntityMapper<Block, BlockEntity> {}
