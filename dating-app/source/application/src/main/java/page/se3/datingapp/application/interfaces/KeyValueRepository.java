package page.se3.datingapp.application.interfaces;

import java.util.Optional;

public interface KeyValueRepository {

    Optional<String> findByKey(String key);

    Optional<String> findByKeyForUpdate(String key);

    void saveOrReplace(String key, String value);
}
