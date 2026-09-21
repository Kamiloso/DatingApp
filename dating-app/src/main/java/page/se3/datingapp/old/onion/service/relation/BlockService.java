package page.se3.datingapp.old.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockAccessDenied;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockAlreadyExists;
import page.se3.datingapp.old.infrastructure.exception.reason.BlockNotFound;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.model.request.BlockUserRequest;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.struct.post.BlockPost;
import page.se3.datingapp.old.onion.accessor.BlockAccessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockAccessor blockAccessor;

    public List<Block> getList(AuthKey authKey) {
        return blockAccessor.getByIdUserOwner(authKey.id());
    }

    public Block add(AuthKey authKey, BlockUserRequest blockUserRequest) throws BlockAlreadyExists {
        BlockPost blockPost = blockUserRequest.derivePost(authKey.id());

        try {
            return blockAccessor.tryPost(blockPost);
        } catch (DataIntegrityViolation e) {
            throw new BlockAlreadyExists();
        }
    }

    public void remove(AuthKey authKey, Long idBlock) throws BlockNotFound, BlockAccessDenied {
        var ignored = checkExistenceAndPrivileges(authKey, idBlock);

        blockAccessor.removeById(idBlock);
    }

    private Block checkExistenceAndPrivileges(AuthKey authKey, Long idBlock)
            throws BlockNotFound, BlockAccessDenied {
        Block block = blockAccessor.getById(idBlock)
                .orElseThrow(BlockNotFound::new);

        if (!block.containsAsOwnerIdUser(authKey.id())) {
            throw new BlockAccessDenied();
        }

        return block;
    }
}
