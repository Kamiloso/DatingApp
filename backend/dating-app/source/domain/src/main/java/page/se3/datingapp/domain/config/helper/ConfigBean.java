package page.se3.datingapp.domain.config.helper;

public interface ConfigBean {

    default boolean areFieldsValid() {
        return true;
    }
}
