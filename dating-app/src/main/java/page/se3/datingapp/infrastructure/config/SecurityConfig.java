package page.se3.datingapp.infrastructure.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import page.se3.datingapp.model.DataScheme;

import java.util.List;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppConfig appConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        AppConfig.Auth authConfig = appConfig.auth();
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(authConfig.url());

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                getIssuerValidator(authConfig),
                getAudienceValidator(authConfig),
                getEmailValidator(authConfig)
        ));

        return jwtDecoder;
    }

    private static OAuth2TokenValidator<@NonNull Jwt> getIssuerValidator(AppConfig.Auth authConfig) {
        return JwtValidators.createDefaultWithIssuer(authConfig.url());
    }

    private static OAuth2TokenValidator<@NonNull Jwt> getAudienceValidator(AppConfig.Auth authConfig) {
        return jwt -> {
            List<String> audience = jwt.getAudience();

            return audience != null && audience.contains(authConfig.clientId())
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token"));
        };
    }

    private static OAuth2TokenValidator<@NonNull Jwt> getEmailValidator(AppConfig.Auth authConfig) {
        Pattern emailPattern = Pattern.compile(authConfig.allowedEmailRegex());

        return jwt -> {
            String email = jwt.getClaimAsString("email");

            return email != null && email.length() <= DataScheme.LEN_255 && emailPattern.matcher(email).matches()
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_email"));
        };
    }
}
