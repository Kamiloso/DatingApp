package page.se3.datingapp.old.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.onion.accessor.BlockAccessor;
import page.se3.datingapp.old.onion.accessor.ChatAccessor;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final BlockAccessor blockAccessor;
    private final ChatAccessor chatAccessor;

    public List<Chat> getList(AuthKey authKey, boolean blocked) {
        Set<Long> blockSet = MapUtils.toSet(
                blockAccessor.getByIdUserOwner(authKey.id()),
                Block::idUserBlocked
        );

        return chatAccessor.getByIdUser(authKey.id()).stream()
                .filter(c -> blocked || !(blockSet.contains(c.idUserFirst()) || blockSet.contains(c.idUserSecond())))
                .toList();
    }
}
