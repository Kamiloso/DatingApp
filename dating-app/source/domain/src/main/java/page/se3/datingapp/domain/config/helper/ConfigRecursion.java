package page.se3.datingapp.domain.config.helper;

import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ConfigRecursion {

    private ConfigRecursion() {}

    public static boolean isValid(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof List<?> list) {
            return list.stream().allMatch(ConfigRecursion::isValid);
        }

        if (!(obj instanceof ConfigBean config)) {
            return true;
        }

        boolean foundInvalid = getRecordMembers(config).stream()
                .anyMatch(x -> !ConfigRecursion.isValid(x));

        return !foundInvalid && config.areFieldsValid();
    }

    public static List<ConfigBean> findAllBeans(Object obj) {
        if (!(obj instanceof ConfigBean config)) {
            return List.of();
        }

        var s1 = Stream.of(config);
        var s2 = getRecordMembers(config).stream()
                .flatMap(x -> findAllBeans(x).stream());

        return Stream.concat(s1, s2).toList();
    }

    public static List<Object> getRecordMembers(Object obj) {
        if (!(obj instanceof Record record)) {
            return List.of();
        }

        Class<?> clazz = record.getClass();
        List<Object> members = new ArrayList<>();

        for (RecordComponent component : clazz.getRecordComponents()) {
            try {
                members.add(component.getAccessor().invoke(record));
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }

        return members;
    }
}
