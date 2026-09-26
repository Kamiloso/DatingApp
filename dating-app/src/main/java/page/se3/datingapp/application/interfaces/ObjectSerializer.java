package page.se3.datingapp.application.interfaces;

import java.util.Optional;

public interface ObjectSerializer {

    <T> String serialize(T obj);

    <T> Optional<T> deserialize(String string, Class<T> clazz);
}
