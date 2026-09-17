package page.se3.datingapp.core;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class StringKey {

    private final String contents;

    public static StringKey derive(Class<?> clazz) {
        return new StringKey(clazz.getTypeName());
    }

    public StringKey add(Object obj) {
        return new StringKey(contents + ":" + obj);
    }

    public String get() {
        return contents;
    }

    @Override
    @NonNull
    public String toString() {
        return String.valueOf(contents);
    }
}
