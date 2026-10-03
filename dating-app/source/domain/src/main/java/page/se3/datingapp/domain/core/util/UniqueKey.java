package page.se3.datingapp.domain.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UniqueKey {

    private static final Map<String, Class<?>> registry = new ConcurrentHashMap<>();

    private UniqueKey() {}

    public static String of(Class<?> clazz) {
        String uniqueName = clazz.getSimpleName();
        Class<?> existing = registry.putIfAbsent(uniqueName, clazz);

        if (existing != null && !existing.equals(clazz)) {
            throw new IllegalStateException("Unique name conflict in class '" + uniqueName + "'.");
        }

        return uniqueName;
    }

    public static String merge(String key1, String key2) {
        return key1 + ":" + key2;
    }
}
