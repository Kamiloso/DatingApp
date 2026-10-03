package page.se3.datingapp.infrastructure.controller.rest.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.auth.AuthService;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailRegexMismatch;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailSendingFailed;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidCredentials;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidVerificationCode;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.rest.auth.dto.*;
import page.se3.datingapp.infrastructure.controller.service.Aborting;
import page.se3.datingapp.infrastructure.controller.service.Response;

@Service
@RequiredArgsConstructor
public class AuthProxy {

    private final Aborting aborting;
    private final AuthService authService;
    private final AuthEmailValidator authEmailValidator;

    public ResponseEntity<@NonNull Void> sendVerificationCode(SendVerificationCodeDto sendVerificationCodeDto)
            throws EmailRegexMismatch, EmailSendingFailed {
        String email = authEmailValidator.checkEmail(sendVerificationCodeDto.email());
        authService.sendCode(email);
        return Response.noContent();
    }

    @AutoRetry
    public ResponseEntity<@NonNull Void> setPasswordByVerificationCode(
            SetPasswordByVerificationCodeDto setPasswordByVerificationCodeDto
    ) throws InvalidVerificationCode {
        authService.setPasswordByVerificationCode(
                setPasswordByVerificationCodeDto.verificationCode(),
                setPasswordByVerificationCodeDto.newPassword()
        );
        return Response.noContent();
    }

    // TODO: fix set password

    @AutoRetry
    public ResponseEntity<@NonNull Void> setPassword(String email, SetPasswordDto setPasswordDto) {
        authService.setPassword(email, setPasswordDto.newPassword());
        return Response.noContent();
    }

    @AutoRetry
    public JwtTokenDto computeJwtByLogin(LoginDto loginDto) throws EmailRegexMismatch, InvalidCredentials {
        String email = authEmailValidator.checkEmail(loginDto.email());
        String jwtToken = authService.getJwtByLogin(email, loginDto.password());
        return new JwtTokenDto(jwtToken);
    }

    @AutoRetry
    public JwtTokenDto getJwtByDebugLogin(DebugLoginDto debugLoginDto)
            throws DebugOnlyEndpoint, EmailRegexMismatch {
        aborting.throwOnProduction();

        String email = authEmailValidator.checkEmail(debugLoginDto.email());
        String jwtToken = authService.getJwtByEmail(email);
        return new JwtTokenDto(jwtToken);
    }

    @AutoRetry
    public JwtTokenDto refreshJwt(String email) {
        String jwtToken = authService.getJwtByEmail(email);
        return new JwtTokenDto(jwtToken);
    }
}
