package page.se3.datingapp.application.service.auth.interfaces;

import page.se3.datingapp.domain.exception.abort.reason.auth.EmailSendingFailed;

public interface EmailSender {

    void send(String email, String topic, String contents) throws EmailSendingFailed;
}
