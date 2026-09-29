package page.se3.datingapp.domain.model.block;

public record BlockAdd(
        Long idUserBlocked,
        Boolean isSilent
) {

    public Block deriveInitial(Long idUserOwner) {
        return Block.getInitial(idUserOwner, idUserBlocked, isSilent);
    }
}
