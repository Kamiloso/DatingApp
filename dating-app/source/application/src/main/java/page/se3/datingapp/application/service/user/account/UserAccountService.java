package page.se3.datingapp.application.service.user.account;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.repository.UserRepository;
import page.se3.datingapp.application.service.matching.index.MatchingIndexer;
import page.se3.datingapp.domain.model.user.account.UserAccountBasePatch;
import page.se3.datingapp.domain.model.user.account.UserAccountDetailPatch;
import page.se3.datingapp.domain.model.user.account.UserAccountRegister;
import page.se3.datingapp.domain.exception.UnexpectedRuntimeException;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountPatchDenied;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.model.user.account.UserAccount;

@RootService
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepository userRepository;
    private final MatchingIndexer matchingIndexer;

    public AuthKey getAuthKey(String email) throws AccountNotFound {
        return get(email).getAuthKey(); // TODO: add caching
    }

    public UserAccount get(String email) throws AccountNotFound {
        return userRepository.findByEmail(email)
                .map(UserAccount::getFrom)
                .orElseThrow(AccountNotFound::new);
    }

    public UserAccount register(
            String email,
            UserAccountRegister userAccountRegister
    ) throws AccountAlreadyExists {
        int futureMatchingIndex = matchingIndexer.getFuture();

        User user = userAccountRegister.deriveInitial(email, futureMatchingIndex);

        try {
            return UserAccount.getFrom(userRepository.trySave(user));
        } catch (DatabaseConflictException e) {
            throw new AccountAlreadyExists();
        }
    }

    @ReadCommitted
    public UserAccount patchDetail(AuthKey authKey, UserAccountDetailPatch userAccountDetailPatch)
            throws AccountNotFound {
        int futureMatchingIndex = matchingIndexer.getFuture();

        User user = userRepository.findByIdForUpdate(authKey.id())
                .orElseThrow(AccountNotFound::new);

        user = userAccountDetailPatch.applyPatch(user, futureMatchingIndex);

        try {
            return UserAccount.getFrom(userRepository.trySave(user));
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    @ReadCommitted
    public UserAccount patchBase(AuthKey authKey, UserAccountBasePatch userAccountBasePatch)
            throws AccountNotFound, AccountPatchDenied {
        User user = userRepository.findByIdForUpdate(authKey.id())
                .orElseThrow(AccountNotFound::new);

        if (user.isPatched()) {
            throw new AccountPatchDenied();
        }

        user = userAccountBasePatch.applyPatch(user);

        try {
            return UserAccount.getFrom(userRepository.trySave(user));
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }
}
