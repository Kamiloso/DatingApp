package page.se3.datingapp.onion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JsonSerializer {

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
