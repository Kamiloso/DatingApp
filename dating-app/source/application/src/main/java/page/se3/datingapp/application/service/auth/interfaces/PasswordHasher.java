package page.se3.datingapp.application.service.auth.interfaces;

public interface PasswordHasher {

    String hash(String password);

    boolean verify(String password, String passwordHash);
}
