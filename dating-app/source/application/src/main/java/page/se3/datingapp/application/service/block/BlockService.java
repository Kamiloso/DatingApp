package page.se3.datingapp.application.service.block;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.block.struct.BlockAdd;
import page.se3.datingapp.domain.exception.abort.reason.*;
import page.se3.datingapp.application.repository.BlockRepository;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionConflict;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockSelfConflict;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.util.ArrayList;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    public List<Block> getList(AuthKey authKey, boolean owned, boolean received) {
        List<Block> blockList = new ArrayList<>();

        if (owned) {
            blockList.addAll(blockRepository.findAllByIdUserOwner(authKey.id()));
        }

        if (received) {
            blockList.addAll(blockRepository.findAllByIdUserBlockedWhereNotSilent(authKey.id()));
        }

        return blockList;
    }

    public Block add(AuthKey authKey, BlockAdd blockAdd) throws BlockSelfConflict, BlockInsertionConflict {
        Block block = blockAdd.deriveInitial(authKey.id());

        if (block.isSelfBlocked()) {
            throw new BlockSelfConflict();
        }

        try {
            return blockRepository.trySave(block);
        } catch (DataIntegrityViolation e) {
            throw new BlockInsertionConflict();
        }
    }

    public void remove(AuthKey authKey, Long idBlock) throws BlockNotFound, BlockAccessDenied {
        Block block = blockRepository.findById(idBlock)
                .orElseThrow(BlockNotFound::new);

        if (!block.containsIdUserAsOwner(authKey.id())) {
            throw new BlockAccessDenied();
        }

        blockRepository.deleteById(idBlock);
    }
}
