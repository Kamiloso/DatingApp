package page.se3.datingapp.application.interfaces;

import java.util.function.Consumer;

public interface DistributedEventSystem {

    <T> void invoke(String channelKey, T obj);

    <T> void subscribe(String channelKey, Consumer<T> consumer, Class<T> clazz);
}
