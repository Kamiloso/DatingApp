package page.se3.datingapp.onion.service.matching.tool;

import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.struct.dto.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockMap {
    private final Map<Long, Set<Long>> map = new HashMap<>();

    public BlockMap(List<Block> blockList) {
        for (Block block : blockList) {
            map.computeIfAbsent(block.idUserOwner(), k -> new HashSet<>())
                    .add(block.idUserBlocked());
        }
    }

    public boolean isBlockedPair(User userMain, User userMatch) {
        Set<Long> mainBlocked = map.get(userMain.id());
        Set<Long> matchBlocked = map.get(userMatch.id());

        boolean isMatchBlockedByMain = mainBlocked != null && mainBlocked.contains(userMatch.id());
        boolean isMainBlockedByMatch = matchBlocked != null && matchBlocked.contains(userMain.id());

        return isMatchBlockedByMain || isMainBlockedByMatch;
    }
}
