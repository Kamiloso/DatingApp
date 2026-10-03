package page.se3.datingapp.application.service.auth.interfaces;

public interface JwtProvider {

    String computeFromEmail(String email);
}
