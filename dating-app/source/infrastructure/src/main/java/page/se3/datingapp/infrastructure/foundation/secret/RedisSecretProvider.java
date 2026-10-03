package page.se3.datingapp.infrastructure.foundation.secret;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.core.util.UniqueKey;

import java.security.SecureRandom;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class RedisSecretProvider {

    private static final String UNIQUE_KEY = UniqueKey.of(RedisSecretProvider.class);

    private final StringRedisTemplate redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();

    private volatile String cachedSecret;

    public byte[] getSecretBytes(String key, int length) {
        if (cachedSecret == null) {
            String redisKey = UniqueKey.merge(UNIQUE_KEY, key);
            String secret = generateSecureString(length);

            boolean inserted = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(redisKey, secret));

            if (!inserted) {
                secret = redisTemplate.opsForValue().get(redisKey);

                if (secret == null) {
                    throw new IllegalStateException("Failed to retrieve JWT secret from Redis");
                }
            }

            cachedSecret = secret;
        }

        return Base64.getDecoder().decode(cachedSecret);
    }

    private String generateSecureString(int length) {
        byte[] keyBytes = new byte[length];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
