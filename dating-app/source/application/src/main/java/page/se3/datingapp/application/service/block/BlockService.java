package page.se3.datingapp.application.service.block;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockNotFound;
import page.se3.datingapp.domain.model.block.BlockAdd;
import page.se3.datingapp.application.service.block.repository.BlockRepository;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.block.BlockInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.block.SelfBlockingConflict;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.util.ArrayList;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    public Block get(AuthKey authKey, Long idBlock) throws BlockNotFound, BlockAccessDenied {
        Block block = blockRepository.findById(idBlock)
                .orElseThrow(BlockNotFound::new);

        if (!block.containsIdUserAsOwner(authKey.id())) {
            throw new BlockAccessDenied();
        }

        return block;
    }

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

    public Block add(AuthKey authKey, BlockAdd blockAdd) throws SelfBlockingConflict, BlockInsertionFailed {
        Block block = blockAdd.deriveInitial(authKey.id());

        if (block.isSelfBlocked()) {
            throw new SelfBlockingConflict();
        }

        try {
            return blockRepository.trySave(block);
        } catch (DatabaseConflictException e) {
            throw new BlockInsertionFailed();
        }
    }

    public void remove(AuthKey authKey, Long idBlock) throws BlockAccessDenied {
        Block block = blockRepository.findById(idBlock).orElse(null);
        if (block == null) {
            return;
        }

        if (!block.containsIdUserAsOwner(authKey.id())) {
            throw new BlockAccessDenied();
        }

        blockRepository.deleteById(idBlock);
    }
}
