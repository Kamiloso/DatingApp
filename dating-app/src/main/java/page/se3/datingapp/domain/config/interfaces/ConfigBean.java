package page.se3.datingapp.domain.config.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public interface ConfigBean extends HasChildren {

    static List<ConfigBean> findRecursive(Object obj) {
        if (!(obj instanceof ConfigBean config)) {
            return List.of();
        }

        return Stream.concat(
                Stream.of(config),
                Arrays.stream(config.getChildren())
                        .flatMap(x -> ConfigBean.findRecursive(x).stream())
        ).toList();
    }
}
