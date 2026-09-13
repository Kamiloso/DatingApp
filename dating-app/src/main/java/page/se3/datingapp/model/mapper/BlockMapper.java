package page.se3.datingapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.struct.entity.BlockEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {

    Block toDto(BlockEntity blockEntity);
}
