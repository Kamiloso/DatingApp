package page.se3.datingapp.infrastructure.controller.rest.block;

import lombok.RequiredArgsConstructor;
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
import page.se3.datingapp.infrastructure.controller.service.ResponseFactory;
import page.se3.datingapp.infrastructure.controller.rest.block.dto.BlockDto;
import page.se3.datingapp.infrastructure.controller.service.helper.Response;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class BlockProxy {

    private final BlockService blockService;
    private final BlockMapper blockMapper;
    private final ResponseFactory responseFactory;

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

    public Response<BlockDto> createBlock(AuthKey authKey, BlockAddDto blockAddDto)
            throws SelfBlockingConflict, BlockInsertionFailed {
        BlockAdd blockAdd = blockMapper.toDomain(blockAddDto);

        BlockDto blockDto = Streams.map(
                blockService.add(authKey, blockAdd),
                blockMapper::toDto
        );

        return responseFactory.created(blockDto, blockDto.id());
    }

    public Response<Void> deleteBlock(AuthKey authKey, Long idBlock)
            throws BlockAccessDenied {
        blockService.remove(authKey, idBlock);
        return responseFactory.noContent();
    }
}
