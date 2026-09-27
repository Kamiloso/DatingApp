package page.se3.datingapp.domain.core.tool;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UniqueKey {

    private static final Set<String> registered = ConcurrentHashMap.newKeySet();

    private final String contents;

    public String get(Object... objects) {
        if (objects == null || objects.length == 0) {
            return contents;
        }

        StringBuilder sb = new StringBuilder(contents);
        for (Object obj : objects) {
            String value = String.valueOf(obj);
            validate(value);
            sb.append(":").append(value);
        }

        return sb.toString();
    }

    public static UniqueKey register(String key) {
        validate(key);

        if (!registered.add(key)) {
            throw new IllegalStateException("Key '" + key + "' is declared multiple times");
        }

        return new UniqueKey(key);
    }

    private static void validate(String value) {
        if (value.contains(":")) {
            throw new IllegalArgumentException("Character ':' is not allowed");
        }
    }

    @Override
    public String toString() {
        return contents;
    }
}