package page.se3.datingapp.infrastructure.controller.rest.block;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.block.BlockService;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.model.block.BlockAdd;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.block.SelfBlockingConflict;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.rest.block.dto.BlockAddDto;
import page.se3.datingapp.infrastructure.controller.service.Response;
import page.se3.datingapp.infrastructure.controller.rest.block.dto.BlockDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class BlockProxy {

    private final BlockService blockService;
    private final BlockMapper blockMapper;

    public BlockDto getBlock(AuthKey authKey, Long idBlock) throws BlockNotFound, BlockAccessDenied {
        return Streams.map(
                blockService.get(authKey, idBlock),
                blockMapper::toDto
        );
    }

    public List<BlockDto> getBlocks(AuthKey authKey, Boolean owned, Boolean received) {
        return Streams.toList(
                blockService.getList(authKey, owned, received),
                blockMapper::toDto
        );
    }

    public ResponseEntity<@NonNull BlockDto> createBlock(AuthKey authKey, BlockAddDto blockAddDto)
            throws SelfBlockingConflict, BlockInsertionFailed {
        BlockAdd blockAdd = blockMapper.toDomain(blockAddDto);

        BlockDto blockDto = Streams.map(
                blockService.add(authKey, blockAdd),
                blockMapper::toDto
        );

        return Response.created(blockDto, blockDto.id());
    }

    public ResponseEntity<@NonNull Void> deleteBlock(AuthKey authKey, Long idBlock)
            throws BlockAccessDenied {
        blockService.remove(authKey, idBlock);
        return Response.noContent();
    }
}
