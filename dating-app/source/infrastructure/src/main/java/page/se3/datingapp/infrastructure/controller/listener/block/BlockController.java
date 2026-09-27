package page.se3.datingapp.infrastructure.controller.listener.block;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.*;
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
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestParam
            Boolean owned,

            @RequestParam
            Boolean received
    ) throws AccountNotFound {
        return blockProxy.getBlocks(jwt, owned, received);
    }

    @PostMapping
    @Operation(summary = "Blocks the specified user")
    public BlockDto createBlock(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestBody
            BlockAddDto blockAddDto
    ) throws AccountNotFound, SelfBlockConflict, BlockAlreadyExists {
        return blockProxy.createBlock(jwt, blockAddDto);
    }

    @DeleteMapping("/{idBlock}")
    @Operation(summary = "Unblocks the specified user")
    public ResponseEntity<@NonNull Void> deleteBlock(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idBlock
    ) throws AccountNotFound, BlockNotFound, BlockAccessDenied {
        return blockProxy.deleteBlock(jwt, idBlock);
    }
}
