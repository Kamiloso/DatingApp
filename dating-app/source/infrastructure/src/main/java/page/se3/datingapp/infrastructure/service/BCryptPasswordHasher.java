package page.se3.datingapp.infrastructure.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.auth.interfaces.PasswordHasher;

@Service
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean verify(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
