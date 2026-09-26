package page.se3.datingapp.application.repository;

import java.util.Optional;

public interface GlobalRepository {

    Optional<String> findByKey(String key);

    Optional<String> findByKeyForUpdate(String key);

    void saveOrReplace(String key, String value);
}
