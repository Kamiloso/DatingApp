package page.se3.datingapp.infrastructure.controller.listener.block;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.block.BlockService;
import page.se3.datingapp.domain.model.block.BlockAdd;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionConflict;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockSelfConflict;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockAddDto;
import page.se3.datingapp.infrastructure.controller.uri.Response;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class BlockProxy {

    private final BlockService blockService;
    private final BlockMapper blockMapper;

    public List<BlockDto> getBlocks(AuthKey authKey, Boolean owned, Boolean received) {
        return MapUtils.toList(
                blockService.getList(authKey, owned, received),
                blockMapper::toDto
        );
    }

    public BlockDto createBlock(AuthKey authKey, BlockAddDto blockAddDto)
            throws BlockSelfConflict, BlockInsertionConflict {
        BlockAdd blockAdd = blockMapper.toDomain(blockAddDto);

        return MapUtils.map(
                blockService.add(authKey, blockAdd),
                blockMapper::toDto
        );
    }

    public ResponseEntity<@NonNull Void> deleteBlock(AuthKey authKey, Long idBlock)
            throws BlockAccessDenied {
        blockService.remove(authKey, idBlock);

        return Response.noContent();
    }
}
