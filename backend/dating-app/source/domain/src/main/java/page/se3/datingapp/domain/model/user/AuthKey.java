package page.se3.datingapp.domain.model.user;

import page.se3.datingapp.domain.enums.Gender;

public record AuthKey(
        Long id,
        String email,
        Gender gender
) {}
