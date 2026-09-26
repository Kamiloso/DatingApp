package page.se3.datingapp.application.service.message.channel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.SubService;
import page.se3.datingapp.application.interfaces.DistributedEventSystem;
import page.se3.datingapp.application.repository.MessageRepository;
import page.se3.datingapp.application.service.message.struct.MessageFetchResponse;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.application.service.message.struct.MessageSendRequest;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@SubService
@RequiredArgsConstructor
public class MessageChannelManager {

    private static final String CHANNEL_NAME = "msg-channel";

    private final DistributedEventSystem distributedEventSystem;
    private final MessageRepository messageRepository;
    private final ConcurrentMap<Long, Set<MessageFetchObserver>> observers = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        distributedEventSystem.subscribe(CHANNEL_NAME, this::onReceiveMessage, Message.class);
    }

    public Message send(
            UserKey userKey,
            Long idChat,
            MessageSendRequest messageSendRequest
    ) throws DataIntegrityViolation {
        Message message = messageSendRequest.deriveInitial(Instant.now(), idChat, userKey.id());

        message = messageRepository.trySave(message);
        distributedEventSystem.invoke(CHANNEL_NAME, message);

        return message;
    }

    private void onReceiveMessage(Message message) {
        Set<MessageFetchObserver> taskSet = observers.get(message.idChat());
        if (taskSet != null) {
            taskSet.forEach(task -> task.offerDirtyMessage(message));
        }
    }

    public CompletableFuture<MessageFetchResponse> fetch(
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) {
        MessageFetchObserver messageFetchObserver = new MessageFetchObserver(idChat, maxListSize, waitTimeout);

        Runnable cleanup = addObserversAndGetCleanup(idChat, messageFetchObserver);
        messageFetchObserver.onCleanup(cleanup);

        CompletableFuture<MessageFetchResponse> completableFuture = messageFetchObserver.getCompletableFuture();
        List<Message> dbMessages;

        try {
            dbMessages = messageRepository.findAllByIdChatForApplicationPurposes(idChat, lastFetchTime, maxListSize);
        } catch (RuntimeException e) {
            messageFetchObserver.completeExceptionally(e);
            return completableFuture;
        }

        messageFetchObserver.offerPersistentMessages(dbMessages);
        return completableFuture;
    }

    private Runnable addObserversAndGetCleanup(Long idChat, MessageFetchObserver messageFetchObserver) {
        observers.compute(idChat, (k, s) -> {
            if (s == null) {
                s = ConcurrentHashMap.newKeySet();
            }
            s.add(messageFetchObserver);
            return s;
        });

        return () -> observers.computeIfPresent(idChat, (k, s) -> {
            s.remove(messageFetchObserver);
            return s.isEmpty() ? null : s;
        });
    }
}
