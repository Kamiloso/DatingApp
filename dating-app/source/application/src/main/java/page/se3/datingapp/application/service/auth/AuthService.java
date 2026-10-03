package page.se3.datingapp.application.service.auth;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.auth.interfaces.CodeVerificationSystem;
import page.se3.datingapp.application.service.auth.interfaces.EmailSender;
import page.se3.datingapp.application.service.auth.interfaces.JwtProvider;
import page.se3.datingapp.application.service.auth.interfaces.PasswordHasher;
import page.se3.datingapp.application.service.auth.repository.AccountRepository;
import page.se3.datingapp.domain.config.app.auth.verificationcode.AuthVerificationCodeConfig;
import page.se3.datingapp.domain.config.app.email.message.EmailMessageConfig;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.exception.UnexpectedRuntimeException;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailSendingFailed;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidCredentials;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidVerificationCode;
import page.se3.datingapp.domain.model.account.Account;

import java.time.Duration;

@RootService
@RequiredArgsConstructor
public class AuthService {

    private final EmailMessageConfig emailMessageConfig;
    private final AuthVerificationCodeConfig authVerificationCodeConfig;
    private final JwtProvider jwtProvider;
    private final PasswordHasher passwordHasher;
    private final CodeVerificationSystem codeVerificationSystem;
    private final EmailSender emailSender;
    private final AccountRepository accountRepository;

    public void sendCode(String email) throws EmailSendingFailed {
        Duration lifetime = Duration.ofMillis(authVerificationCodeConfig.expirationMillis());
        String code = codeVerificationSystem.obtainAndRemember(email, lifetime);

        emailSender.send(email,
                emailMessageConfig.topic(),
                emailMessageConfig.contents().replace("#CODE", code)
        );
    }

    public void setPasswordByVerificationCode(String verificationCode, String newPassword)
            throws InvalidVerificationCode {
        String email = codeVerificationSystem.verifyAndTakeEmail(verificationCode)
                .orElseThrow(InvalidVerificationCode::new);

        setPassword(email, newPassword);
    }

    public void setPassword(String email, String newPassword) {
        Account account = Account.getSelfInitial(email, passwordHasher.hash(newPassword));

        try {
            accountRepository.trySave(account);
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    public String getJwtByLogin(String email, String password) throws InvalidCredentials {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(InvalidCredentials::new);

        if (!passwordHasher.verify(password, account.passwordHash())) {
            throw new InvalidCredentials();
        }

        return jwtProvider.computeFromEmail(email);
    }

    public String getJwtByEmail(String email) {
        return jwtProvider.computeFromEmail(email);
    }
}
