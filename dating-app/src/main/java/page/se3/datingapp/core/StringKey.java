package page.se3.datingapp.core;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StringKey {

    private final String contents;

    public static StringKey create(Object obj) {
        return new StringKey(normalize(obj));
    }

    public StringKey with(Object obj) {
        return new StringKey(contents + ":" + normalize(obj));
    }

    public String get() {
        return contents;
    }

    private static String normalize(Object obj) {
        String sourceString = obj instanceof Class<?> clazz
                ? "CLASS=" + clazz.getTypeName()
                : String.valueOf(obj);

        return sourceString
                .replace("%", "%A")
                .replace(":", "%B");
    }

    @Override
    public String toString() {
        return contents;
    }
}
