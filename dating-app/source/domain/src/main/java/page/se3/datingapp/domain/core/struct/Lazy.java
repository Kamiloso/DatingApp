package page.se3.datingapp.domain.core.struct;

import java.util.function.Supplier;

public final class Lazy<T> {

    private volatile Supplier<T> initializer;
    private volatile T contents;

    private Lazy() {}

    public static <T> Lazy<T> of(Supplier<T> initializer) {
        Lazy<T> lazy = new Lazy<>();
        lazy.initializer = initializer;
        return lazy;
    }

    public T get() {
        if (initializer == null) {
            return contents;
        }

        synchronized (this) {
            if (initializer != null) {
                contents = initializer.get();
                initializer = null;
            }
        }

        return contents;
    }
}
