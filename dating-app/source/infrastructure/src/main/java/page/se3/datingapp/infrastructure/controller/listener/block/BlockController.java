package page.se3.datingapp.infrastructure.controller.listener.block;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionConflict;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockSelfConflict;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockDto;
import page.se3.datingapp.infrastructure.controller.listener.block.dto.BlockAddDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Blocks")
@RequestMapping("/api/relations/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockProxy blockProxy;

    @GetMapping
    @Operation(summary = "Retrieves blocks that the user is aware of")
    public List<BlockDto> getBlocks(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Boolean owned,

            @RequestParam
            Boolean received
    ) {
        return blockProxy.getBlocks(authKey, owned, received);
    }

    @PostMapping
    @Operation(summary = "Blocks the specified user")
    public BlockDto createBlock(
            @AuthIdentity
            AuthKey authKey,

            @RequestBody
            BlockAddDto blockAddDto
    ) throws BlockSelfConflict, BlockInsertionConflict {
        return blockProxy.createBlock(authKey, blockAddDto);
    }

    @DeleteMapping("/{idBlock}")
    @Operation(summary = "Unblocks the specified user")
    public ResponseEntity<@NonNull Void> deleteBlock(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idBlock
    ) throws BlockNotFound, BlockAccessDenied {
        return blockProxy.deleteBlock(authKey, idBlock);
    }
}
