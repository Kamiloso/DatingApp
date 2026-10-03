package page.se3.datingapp.infrastructure.config.spring;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import page.se3.datingapp.domain.core.util.UniqueKey;
import page.se3.datingapp.infrastructure.foundation.secret.RedisSecretProvider;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private static final String UNIQUE_KEY = UniqueKey.of(JwtConfig.class);
    private static final int SECRET_LENGTH = 64;

    private final RedisSecretProvider redisSecretProvider;

    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] secretBytes = redisSecretProvider.getSecretBytes(UNIQUE_KEY, SECRET_LENGTH);
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretBytes));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] secretBytes = redisSecretProvider.getSecretBytes(UNIQUE_KEY, SECRET_LENGTH);
        SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
