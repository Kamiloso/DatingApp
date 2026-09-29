package page.se3.datingapp.infrastructure.controller.listener.block;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.block.BlockAdd;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockAddDto;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {

    BlockDto toDto(Block block);
    BlockAdd toDomain(BlockAddDto blockAddDto);
}
