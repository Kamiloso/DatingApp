package page.se3.datingapp.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import page.se3.datingapp.repository.redis.RedisRepository;

import java.io.UncheckedIOException;
import java.util.Optional;

@RequiredArgsConstructor
public class RedisSerializer<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RedisRepository redisRepository;
    private final Class<T> clazz;

    public void set(String key, T obj) {
        redisRepository.set(key, serialize(obj));
    }

    public boolean add(String key, T obj) {
        return redisRepository.add(key, serialize(obj));
    }

    public boolean delete(String key) {
        return redisRepository.delete(key);
    }

    public Optional<T> find(String key) {
        return redisRepository.find(key).map(this::deserialize);
    }

    public Optional<T> take(String key) {
        return redisRepository.take(key).map(this::deserialize);
    }

    private String serialize(T obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Serialization failed", e);
        }
    }

    private T deserialize(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Deserialization failed", e);
        }
    }
}
