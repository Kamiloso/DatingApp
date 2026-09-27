package page.se3.datingapp.domain.model.user;

import page.se3.datingapp.domain.enums.Gender;

public record UserKey(
        Long id,
        Gender gender
) {}
