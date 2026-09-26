package page.se3.datingapp.application.service.block.struct;

import page.se3.datingapp.domain.model.block.Block;

public record BlockAddRequest(
        Long idUserBlocked,
        Boolean isSilent
) {

    public Block deriveInitial(Long idUserOwner) {
        return Block.getInitial(idUserOwner, idUserBlocked, isSilent);
    }
}
