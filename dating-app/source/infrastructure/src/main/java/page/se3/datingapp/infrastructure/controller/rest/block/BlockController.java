package page.se3.datingapp.infrastructure.controller.rest.block;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.exception.abort.reason.block.SelfBlockingConflict;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.block.dto.BlockDto;
import page.se3.datingapp.infrastructure.controller.rest.block.dto.BlockAddDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Blocks")
@RequestMapping("/api/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final BlockProxy blockProxy;

    @GetMapping("/{idBlock}")
    @Operation(summary = "Gets a specific block")
    public BlockDto getBlock(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idBlock
    ) throws BlockNotFound, BlockAccessDenied {
        return blockProxy.getBlock(authKey, idBlock);
    }

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<@NonNull BlockDto> createBlock(
            @AuthIdentity
            AuthKey authKey,

            @Valid
            @NotNull
            @RequestBody
            BlockAddDto blockAddDto
    ) throws SelfBlockingConflict, BlockInsertionFailed {
        return blockProxy.createBlock(authKey, blockAddDto);
    }

    @DeleteMapping("/{idBlock}")
    @Operation(summary = "Unblocks the specified user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<@NonNull Void> deleteBlock(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idBlock
    ) throws BlockAccessDenied {
        return blockProxy.deleteBlock(authKey, idBlock);
    }
}
