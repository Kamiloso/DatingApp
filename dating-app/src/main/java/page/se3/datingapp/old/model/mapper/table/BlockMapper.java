package page.se3.datingapp.old.model.mapper.table;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.struct.entity.BlockEntity;
import page.se3.datingapp.old.model.struct.post.BlockPost;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {

    @Mapping(target = "id", ignore = true)
    BlockEntity toEntity(BlockPost blockPost);

    Block toDto(BlockEntity blockEntity);
}
