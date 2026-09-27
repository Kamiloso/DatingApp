package page.se3.datingapp.application.service.user.account;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.repository.UserRepository;
import page.se3.datingapp.application.service.user.account.struct.UserAccountRegister;
import page.se3.datingapp.domain.exception.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.model.user.account.UserAccount;

@RootService
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepository userRepository;

    public UserAccount get(String email) throws AccountNotFound {
        return userRepository.findByEmail(email)
                .map(UserAccount::getFrom)
                .orElseThrow(AccountNotFound::new);
    }

    public UserAccount register(
            String email,
            UserAccountRegister userAccountRegister
    ) throws AccountAlreadyExists {
        User user = userAccountRegister.deriveInitial(email);

        try {
            return UserAccount.getFrom(userRepository.trySave(user));
        } catch (DataIntegrityViolation e) {
            throw new AccountAlreadyExists();
        }
    }
}
