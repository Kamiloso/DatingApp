package page.se3.datingapp.onion.service.msg.tool;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.model.struct.dto.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DelayedMsgFetchTask {

    private enum State {STARTED, DB_FETCHED, FINALIZED}

    @Getter
    private final DeferredResult<@NonNull List<Message>> deferredResult;

    private final Long idChat;
    private final Integer maxListSize;

    private State state = State.STARTED;
    private final List<Message> bufferedMessages = new ArrayList<>();

    public DelayedMsgFetchTask(Long idChat, Integer maxListSize, Long waitTimeout) {
        this.idChat = idChat;
        this.maxListSize = maxListSize;
        this.deferredResult = new DeferredResult<>(waitTimeout, Collections.emptyList());
    }

    public synchronized void offerFreshMessage(Message message) {
        if (state == State.FINALIZED || !Objects.equals(this.idChat, message.idChat())) {
            return;
        }

        if (state == State.DB_FETCHED) {
            finalize(List.of(message));
        } else if (bufferedMessages.size() < maxListSize) {
            bufferedMessages.add(message);
        }
    }

    public synchronized void offerDbFetchResult(List<Message> dbMessageList) {
        if (state == State.FINALIZED) {
            return;
        }

        state = State.DB_FETCHED;

        if (!dbMessageList.isEmpty()) {
            finalize(dbMessageList);
        } else if (!bufferedMessages.isEmpty()) {
            finalize(new ArrayList<>(bufferedMessages));
        }
    }

    private void finalize(List<Message> messageList) {
        deferredResult.setResult(messageList);
        state = State.FINALIZED;
    }
}
