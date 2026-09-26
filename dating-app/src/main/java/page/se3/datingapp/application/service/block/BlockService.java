package page.se3.datingapp.application.service.block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.RootService;
import page.se3.datingapp.domain.exception.abort.reason.*;
import page.se3.datingapp.application.repository.BlockRepository;
import page.se3.datingapp.application.service.block.struct.BlockAddRequest;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.model.user.UserKey;

import java.util.ArrayList;
import java.util.List;

@Service
@RootService
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    public List<Block> getList(UserKey userKey, boolean owned, boolean received) {
        List<Block> blockList = new ArrayList<>();

        if (owned) {
            blockList.addAll(blockRepository.findAllByIdUserOwner(userKey.id()));
        }

        if (received) {
            blockList.addAll(blockRepository.findAllByIdUserBlockedWhereNotSilent(userKey.id()));
        }

        return blockList;
    }

    public Block add(UserKey userKey, BlockAddRequest blockAddRequest) throws SelfBlockConflict, BlockAlreadyExists {
        Block block = blockAddRequest.deriveInitial(userKey.id());

        if (block.isSelfBlocked()) {
            throw new SelfBlockConflict();
        }

        try {
            return blockRepository.trySave(block);
        } catch (DataIntegrityViolation e) {
            throw new BlockAlreadyExists();
        }
    }

    public void remove(UserKey userKey, Long idBlock) throws BlockNotFound, BlockAccessDenied {
        Block block = blockRepository.findById(idBlock)
                .orElseThrow(BlockNotFound::new);

        if (!block.containsIdUserAsOwner(userKey.id())) {
            throw new BlockAccessDenied();
        }

        blockRepository.deleteById(idBlock);
    }
}
