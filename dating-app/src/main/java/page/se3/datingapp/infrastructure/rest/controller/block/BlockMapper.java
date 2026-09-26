package page.se3.datingapp.infrastructure.rest.controller.block;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.application.service.block.struct.BlockAddRequest;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.infrastructure.rest.controller.block.dto.BlockAddRequestDto;
import page.se3.datingapp.infrastructure.rest.controller.block.dto.BlockDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {

    BlockDto toDto(Block block);
    BlockAddRequest toDomain(BlockAddRequestDto blockAddRequestDto);
}
