package page.se3.datingapp.infrastructure.controller.listener.block;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.block.BlockService;
import page.se3.datingapp.application.service.block.struct.BlockAdd;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.*;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockAddDto;
import page.se3.datingapp.infrastructure.controller.uri.Response;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockDto;
import page.se3.datingapp.infrastructure.controller.service.JwtProcessor;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class BlockProxy {

    private final JwtProcessor jwtProcessor;
    private final BlockService blockService;
    private final BlockMapper blockMapper;

    public List<BlockDto> getBlocks(Jwt jwt, Boolean owned, Boolean received) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<Block> blockList = blockService.getList(userKey, owned, received);

        return MapUtils.toList(blockList, blockMapper::toDto);
    }

    public BlockDto createBlock(Jwt jwt, BlockAddDto blockAddDto)
            throws AccountNotFound, SelfBlockConflict, BlockAlreadyExists {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        BlockAdd blockAdd = blockMapper.toDomain(blockAddDto);
        Block block = blockService.add(userKey, blockAdd);

        return blockMapper.toDto(block);
    }

    public ResponseEntity<@NonNull Void> deleteBlock(Jwt jwt, Long idBlock)
            throws AccountNotFound, BlockNotFound, BlockAccessDenied {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        blockService.remove(userKey, idBlock);

        return Response.noContent();
    }
}
