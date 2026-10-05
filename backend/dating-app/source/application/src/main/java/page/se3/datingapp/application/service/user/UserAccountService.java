package page.se3.datingapp.application.service.user;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.service.user.repository.UserRepository;
import page.se3.datingapp.application.service.matching.indexer.MatchingIndexer;
import page.se3.datingapp.domain.model.user.account.UserAccountBasePatch;
import page.se3.datingapp.domain.model.user.account.UserAccountDetailPatch;
import page.se3.datingapp.domain.model.user.account.UserAccountRegister;
import page.se3.datingapp.domain.exception.UnexpectedRuntimeException;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotRegistered;
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

    public UserAccount get(String email) throws AccountNotRegistered {
        Integer futureMatchingIndex = matchingIndexer.getFuture();

        return userRepository.findByEmail(email)
                .map(u -> UserAccount.getFrom(u, futureMatchingIndex))
                .orElseThrow(AccountNotRegistered::new);
    }

    public UserAccount register(
            String email,
            UserAccountRegister userAccountRegister
    ) throws AccountInsertionFailed {
        int futureMatchingIndex = matchingIndexer.getFuture();

        User user = userAccountRegister.deriveInitial(email, futureMatchingIndex);

        try {
            return UserAccount.getFrom(userRepository.trySave(user), futureMatchingIndex);
        } catch (DatabaseConflictException e) {
            throw new AccountInsertionFailed();
        }
    }

    public AuthKey getAuthKey(String email) throws AccountNotRegistered {
        return get(email).getAuthKey(); // TODO: add caching
    }

    @ReadCommitted
    public UserAccount patchDetail(AuthKey authKey, UserAccountDetailPatch userAccountDetailPatch)
            throws AccountNotRegistered {
        int futureMatchingIndex = matchingIndexer.getFuture();

        User user = userRepository.findByIdForUpdate(authKey.id())
                .orElseThrow(AccountNotRegistered::new);

        user = userAccountDetailPatch.applyPatch(user, futureMatchingIndex);

        try {
            return UserAccount.getFrom(userRepository.trySave(user), futureMatchingIndex);
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    @ReadCommitted
    public UserAccount patchBase(AuthKey authKey, UserAccountBasePatch userAccountBasePatch)
            throws AccountNotRegistered, AccountPatchDenied {
        int futureMatchingIndex = matchingIndexer.getFuture();

        User user = userRepository.findByIdForUpdate(authKey.id())
                .orElseThrow(AccountNotRegistered::new);

        if (user.isPatched()) {
            throw new AccountPatchDenied();
        }

        user = userAccountBasePatch.applyPatch(user);

        try {
            return UserAccount.getFrom(userRepository.trySave(user), futureMatchingIndex);
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }
}
