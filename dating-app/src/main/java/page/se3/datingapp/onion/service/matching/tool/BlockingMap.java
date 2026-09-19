package page.se3.datingapp.onion.service.matching.tool;

import page.se3.datingapp.model.struct.dto.User;

import java.util.*;
import java.util.function.Function;

public class BlockingMap {

    private final Map<Long, Set<Long>> map = new HashMap<>();

    public <T> void applyList(List<T> objList, Function<T, Long> getIdFirst, Function<T, Long> getIdSecond) {
        for (T obj : objList) {
            map.computeIfAbsent(getIdFirst.apply(obj), k -> new HashSet<>())
                    .add(getIdSecond.apply(obj));

            map.computeIfAbsent(getIdSecond.apply(obj), k -> new HashSet<>())
                    .add(getIdFirst.apply(obj));
        }
    }

    public boolean isUserPairBlocked(User userFirst, User userSecond) {
        return map.getOrDefault(userFirst.id(), Collections.emptySet())
                .contains(userSecond.id());
    }
}
