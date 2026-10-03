package page.se3.datingapp.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.auth.interfaces.JwtProvider;
import page.se3.datingapp.domain.config.app.auth.jwt.AuthJwtConfig;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtOAuth2Provider implements JwtProvider {

    private final AuthJwtConfig authJwtConfig;
    private final JwtEncoder jwtEncoder;

    public String computeFromEmail(String email) {
        Instant now = Instant.now();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(authJwtConfig.issuer())
                .subject(email)
                .issuedAt(now)
                .expiresAt(now.plus(authJwtConfig.expirationMillis(), ChronoUnit.MILLIS))
                .claim("email", email)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
