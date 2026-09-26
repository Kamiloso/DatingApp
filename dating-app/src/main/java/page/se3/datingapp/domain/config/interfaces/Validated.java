package page.se3.datingapp.domain.config.interfaces;

import java.util.Arrays;

public interface Validated extends HasChildren {

    default boolean isValid() {
        return true;
    }

    static boolean isValidRecursive(Object obj) {
        if (!(obj instanceof Validated validated)) {
            return obj != null;
        }

        if (Arrays.stream(validated.getChildren())
                .anyMatch(x -> !Validated.isValidRecursive(x))
        ) return false;

        return validated.isValid();
    }
}
