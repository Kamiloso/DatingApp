package page.se3.datingapp.old.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountNotFound;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockAccessDenied;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockAlreadyExists;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockNotFound;
import page.se3.datingapp.old.infrastructure.util.Response;
import page.se3.datingapp.old.model.request.BlockUserRequest;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.onion.service.user.AuthService;
import page.se3.datingapp.old.onion.service.relation.BlockService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Blocks")
@RequestMapping("/api/relations/blocks")
@RequiredArgsConstructor
public class BlockController {

    private final AuthService authService;
    private final BlockService blockService;

    @GetMapping
    @Operation(summary = "Gets all owned blocks")
    public List<Block> getOwnedBlocks(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return blockService.getList(authKey);
    }

    @PostMapping
    @Operation(summary = "Blocks the given user")
    public Block blockUser(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestBody
            BlockUserRequest blockUserRequest
    ) throws AccountNotFound, BlockAlreadyExists {
        AuthKey authKey = authService.getAuthKey(jwt);
        return blockService.add(authKey, blockUserRequest);
    }

    @DeleteMapping("/{idBlock}")
    @Operation(summary = "Unblocks the given user")
    public ResponseEntity<@NonNull Void> removeUserBlock(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idBlock
    ) throws AccountNotFound, BlockNotFound, BlockAccessDenied {
        AuthKey authKey = authService.getAuthKey(jwt);
        blockService.remove(authKey, idBlock);
        return Response.noContent();
    }
}
