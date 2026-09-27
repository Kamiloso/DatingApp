package page.se3.datingapp.domain.config.interfaces;

public interface HasChildren {

    default Object[] getChildren() {
        return new Object[0];
    }
}
