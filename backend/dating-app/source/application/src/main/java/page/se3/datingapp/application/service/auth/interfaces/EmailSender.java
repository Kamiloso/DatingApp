package page.se3.datingapp.application.service.auth.interfaces;

public interface EmailSender {

    void sendAsync(String email, String topic, String contents);
}
