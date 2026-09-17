package page.se3.datingapp.core;

import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Event<T> {

    private final List<Consumer<T>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void unsubscribe(Consumer<T> listener) {
        listeners.remove(listener);
    }

    public void invoke(T argument) {
        listeners.forEach(listener -> listener.accept(argument));
    }
}
