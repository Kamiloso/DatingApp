package page.se3.datingapp.infrastructure.controller.rest.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.domain.service.email.EmailValidator;
import page.se3.datingapp.application.service.auth.AuthService;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailValidationFailed;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailSendingFailed;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidCredentials;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidVerificationCode;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DebugOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.rest.auth.dto.*;
import page.se3.datingapp.infrastructure.controller.service.FailFast;
import page.se3.datingapp.infrastructure.controller.service.ResponseFactory;
import page.se3.datingapp.infrastructure.controller.struct.Response;

@Service
@RequiredArgsConstructor
public class AuthProxy {

    private final AuthService authService;
    private final EmailValidator emailValidator;
    private final ResponseFactory responseFactory;
    private final FailFast failFast;

    public Response<Void> sendVerificationCode(SendVerificationCodeDto sendVerificationCodeDto)
            throws EmailValidationFailed, EmailSendingFailed {
        String email = emailValidator.checkEmail(sendVerificationCodeDto.email());
        authService.sendCode(email);
        return responseFactory.noContent();
    }

    @AutoRetry
    public Response<Void> setPasswordByVerificationCode(
            SetPasswordByVerificationCodeDto setPasswordByVerificationCodeDto
    ) throws InvalidVerificationCode {
        authService.setPasswordByVerificationCode(
                setPasswordByVerificationCodeDto.verificationCode(),
                setPasswordByVerificationCodeDto.newPassword()
        );
        return responseFactory.noContent();
    }

    @AutoRetry
    public Response<Void> setPasswordBySession(String email, SetPasswordDto setPasswordDto) {
        authService.setPasswordByEmail(email, setPasswordDto.newPassword());
        return responseFactory.noContent();
    }

    @AutoRetry
    public JwtTokenDto computeJwtByLogin(LoginDto loginDto) throws EmailValidationFailed, InvalidCredentials {
        String email = emailValidator.checkEmail(loginDto.email());
        String jwtToken = authService.getJwtByLogin(email, loginDto.password());
        return new JwtTokenDto(jwtToken);
    }

    @AutoRetry
    public JwtTokenDto getJwtByDebugLogin(DebugLoginDto debugLoginDto)
            throws DebugOnlyEndpoint, EmailValidationFailed {
        failFast.abortOnProduction();

        String email = emailValidator.checkEmail(debugLoginDto.email());
        String jwtToken = authService.getJwtByEmail(email);
        return new JwtTokenDto(jwtToken);
    }

    @AutoRetry
    public JwtTokenDto regenerateJwtBySession(String email) {
        String jwtToken = authService.getJwtByEmail(email);
        return new JwtTokenDto(jwtToken);
    }
}
