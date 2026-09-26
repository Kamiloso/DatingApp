package page.se3.datingapp.domain.model.block;

import java.util.*;
import java.util.function.Function;

public class BlockMap {

    private final Map<Long, Set<Long>> map = new HashMap<>();

    private BlockMap() {}

    public static Builder builder() {
        return new Builder();
    }

    public boolean isPairBlocked(Long idUserFirst, Long idUserSecond) {
        return map.getOrDefault(idUserFirst, Collections.emptySet())
                .contains(idUserSecond);
    }

    public static class Builder {

        private final BlockMap instance = new BlockMap();

        public <T> Builder withBlocking(
                List<T> objList,
                Function<T, Long> getIdFirst,
                Function<T, Long> getIdSecond
        ) {
            for (T obj : objList) {
                instance.map.computeIfAbsent(getIdFirst.apply(obj), k -> new HashSet<>())
                        .add(getIdSecond.apply(obj));

                instance.map.computeIfAbsent(getIdSecond.apply(obj), k -> new HashSet<>())
                        .add(getIdFirst.apply(obj));
            }
            return this;
        }

        public BlockMap build() {
            return instance;
        }
    }
}
