package page.se3.datingapp.old.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import page.se3.datingapp.old.model.struct.post.BlockPost;

@Schema(description = "Represents a request to block a user")
public record BlockUserRequest(
        @NotNull
        Long idUserBlocked
) {

    public BlockPost derivePost(Long idUserOwner) {
        return new BlockPost(idUserOwner, idUserBlocked);
    }
}
