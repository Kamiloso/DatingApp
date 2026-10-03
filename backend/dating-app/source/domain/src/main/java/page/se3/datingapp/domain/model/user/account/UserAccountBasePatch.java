package page.se3.datingapp.domain.model.user.account;

import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.model.user.User;

public record UserAccountBasePatch(
        String name,
        String surname,
        Gender gender,
        Integer age
) {

    public User applyPatch(User user) {
        return user.toBuilder()
                .name(name)
                .surname(surname)
                .gender(gender)
                .age(age)
                .isPatched(true)
                .build();
    }
}
