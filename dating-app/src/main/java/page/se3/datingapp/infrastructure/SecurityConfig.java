package page.se3.datingapp.infrastructure;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import page.se3.datingapp.core.Config;

import java.util.List;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Pattern ALLOWED_EMAIL_PATTERN = Pattern.compile(Config.ALLOWED_EMAIL_REGEX);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api-public/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers(request -> {
                            String ip = request.getRemoteAddr();
                            return "127.0.0.1".equals(ip) || "::1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);
                        }).permitAll()
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(Config.AUTH_URL);

        OAuth2TokenValidator<@NonNull Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(Config.AUTH_URL);

        OAuth2TokenValidator<@NonNull Jwt> audienceValidator = jwt -> {
            List<String> audience = jwt.getAudience();
            if (audience == null || !audience.contains(Config.AUTH_CLIENT_ID)) {
                return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token"));
            }
            return OAuth2TokenValidatorResult.success();
        };

        OAuth2TokenValidator<@NonNull Jwt> emailValidator = jwt -> {
            String email = jwt.getClaimAsString("email");
            if (email == null || !ALLOWED_EMAIL_PATTERN.matcher(email).matches()) {
                return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_email"));
            }
            return OAuth2TokenValidatorResult.success();
        };

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                issuerValidator,
                audienceValidator,
                emailValidator
        ));

        return jwtDecoder;
    }
}
