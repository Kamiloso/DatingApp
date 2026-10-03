package page.se3.datingapp.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.auth.interfaces.CodeVerificationSystem;
import page.se3.datingapp.domain.core.util.UniqueKey;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisCodeVerificationSystem implements CodeVerificationSystem {

    private static final String UNIQUE_KEY = UniqueKey.of(RedisCodeVerificationSystem.class);
    private static final String CODE_PATTERN = "XXXX-XXXX-XXXX";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final StringRedisTemplate redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String obtainAndRemember(String email, Duration duration) {
        String code = generateCode();
        String redisKey = UniqueKey.merge(UNIQUE_KEY, code);

        redisTemplate.opsForValue().set(redisKey, email, duration);

        return code;
    }

    @Override
    public Optional<String> verifyAndTakeEmail(String code) {
        String redisKey = UniqueKey.merge(UNIQUE_KEY, code);
        String email = redisTemplate.opsForValue().getAndDelete(redisKey);

        return Optional.ofNullable(email);
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_PATTERN.length());
        for (char c : CODE_PATTERN.toCharArray()) {
            if (c == 'X') {
                sb.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
