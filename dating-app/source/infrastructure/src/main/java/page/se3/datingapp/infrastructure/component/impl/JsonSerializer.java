package page.se3.datingapp.infrastructure.component.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.interfaces.ObjectSerializer;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JsonSerializer implements ObjectSerializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <T> String serialize(T obj) {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    public <T> Optional<T> deserialize(String string, Class<T> clazz) {
        if (string == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(OBJECT_MAPPER.readValue(string, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
