package page.se3.datingapp.infrastructure.foundation.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.core.util.UniqueKey;

import java.security.SecureRandom;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtSecretRedisProvider {

    private static final String UNIQUE_KEY = UniqueKey.of(JwtSecretRedisProvider.class);
    private static final int SECRET_LENGTH_BYTES = 64;

    private final StringRedisTemplate redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();

    private volatile String cachedSecret;

    public byte[] getSecretBytes() {
        String secret = getSecretString();
        return Base64.getDecoder().decode(secret);
    }

    private String getSecretString() {
        if (cachedSecret != null) {
            return cachedSecret;
        }

        String key = UniqueKey.merge(UNIQUE_KEY, "secure-string");
        String secret = generateSecureString();

        boolean inserted = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, secret));

        if (!inserted) {
            secret = redisTemplate.opsForValue().get(key);

            if (secret == null) {
                throw new IllegalStateException("Failed to retrieve JWT secret from Redis");
            }
        }

        return cachedSecret = secret;
    }

    private String generateSecureString() {
        byte[] keyBytes = new byte[SECRET_LENGTH_BYTES];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
