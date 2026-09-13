package page.se3.datingapp.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RedisRepository {
    void set(String key, String value);
    boolean add(String key, String value);
    boolean delete(String key);
    Optional<String> find(String key);
    Optional<String> take(String key);
}

@Repository
@RequiredArgsConstructor
class RedisRepositoryImpl implements RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public boolean add(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Optional<String> find(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public Optional<String> take(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(key));
    }
}
