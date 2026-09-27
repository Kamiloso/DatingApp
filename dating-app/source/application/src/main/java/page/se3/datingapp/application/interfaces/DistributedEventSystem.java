package page.se3.datingapp.application.interfaces;

import java.util.function.Consumer;

public interface DistributedEventSystem {

    <T> void invoke(String channel, T obj);

    <T> void subscribe(String channel, Consumer<T> consumer, Class<T> clazz);
}
