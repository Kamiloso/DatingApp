package page.se3.datingapp.application.service.message.channel;

import lombok.Getter;
import page.se3.datingapp.application.service.message.struct.MessageFetchResponse;
import page.se3.datingapp.domain.model.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MessageFetchObserver {

    private enum State {STARTED, DB_FETCHED, FINALIZED}

    @Getter
    private final CompletableFuture<MessageFetchResponse> completableFuture;

    private final Long idChat;
    private final Integer maxListSize;
    private final List<Message> dirtyMessages = new ArrayList<>();

    private State state = State.STARTED;

    public MessageFetchObserver(Long idChat, Integer maxListSize, Long waitTimeout) {
        this.idChat = idChat;
        this.maxListSize = maxListSize;
        this.completableFuture = new CompletableFuture<MessageFetchResponse>()
                .completeOnTimeout(MessageFetchResponse.empty(), waitTimeout, TimeUnit.MILLISECONDS);
    }

    public synchronized void onCleanup(Runnable cleanup) {
        completableFuture.whenComplete((result, exception) -> cleanup.run());
    }

    public synchronized void completeExceptionally(Throwable e) {
        completableFuture.completeExceptionally(e);
    }

    public synchronized void offerDirtyMessage(Message message) {
        if (state == State.FINALIZED || !Objects.equals(idChat, message.idChat())) {
            return;
        }

        if (dirtyMessages.size() < maxListSize) {
            dirtyMessages.add(message);
        }

        if (state == State.DB_FETCHED) {
            completableFuture.complete(MessageFetchResponse.dirty(dirtyMessages));
            state = State.FINALIZED;
        }
    }

    public synchronized void offerPersistentMessages(List<Message> persistentMessages) {
        if (state == State.FINALIZED) {
            return;
        }

        if (persistentMessages.isEmpty() && dirtyMessages.isEmpty()) {
            state = State.DB_FETCHED;
        } else {
            completableFuture.complete(MessageFetchResponse.both(persistentMessages, dirtyMessages));
            state = State.FINALIZED;
        }
    }
}
