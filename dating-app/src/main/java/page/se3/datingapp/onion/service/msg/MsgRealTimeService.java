package page.se3.datingapp.onion.service.msg;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.core.StringKey;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.onion.service.TransferService;
import page.se3.datingapp.onion.accessor.MessageAccessor;
import page.se3.datingapp.onion.service.msg.tool.DelayedMsgFetchRequest;

import java.time.Instant;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public interface MsgRealTimeService {
    Message sendMessage(MessagePost messagePost) throws DataIntegrityViolation;

    DeferredResult<@NonNull List<Message>> fetchMessageListByIdChat(
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    );
}

@Service
@RequiredArgsConstructor
class MsgRealTimeServiceImpl implements MsgRealTimeService {

    private static final StringKey CHANNEL_KEY = StringKey.create(MsgRealTimeServiceImpl.class);

    private final MessageAccessor messageAccessor;
    private final TransferService transferService;

    private final ConcurrentMap<Long, Queue<DelayedMsgFetchRequest>> pendingRequests = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        transferService.subscribe(CHANNEL_KEY.get(), this::onReceiveMessage, Message.class);
    }

    @Override
    public Message sendMessage(MessagePost messagePost) throws DataIntegrityViolation {
        Message message = messageAccessor.tryPost(messagePost);
        transferService.send(CHANNEL_KEY.get(), message);
        return message;
    }

    private void onReceiveMessage(Message message) {
        Queue<DelayedMsgFetchRequest> requests = pendingRequests.get(message.idChat());
        if (requests != null) {
            for (DelayedMsgFetchRequest request : requests) {
                request.offerFreshMessage(message);
            }
        }
    }

    @Override
    public DeferredResult<@NonNull List<Message>> fetchMessageListByIdChat(
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) {
        DelayedMsgFetchRequest delayedRequest = prepareDelayedMsgFetchRequest(idChat, maxListSize, waitTimeout);

        List<Message> dbMessageList = messageAccessor.getNextMessageListById(idChat, lastFetchTime, maxListSize);
        delayedRequest.offerDbFetchResult(dbMessageList);

        return delayedRequest.getDeferredResult();
    }

    private DelayedMsgFetchRequest prepareDelayedMsgFetchRequest(Long idChat, Integer maxListSize, Long waitTimeout) {
        DelayedMsgFetchRequest delayedRequest = new DelayedMsgFetchRequest(idChat, maxListSize, waitTimeout);

        pendingRequests.compute(idChat, (k, q) -> {
            if (q == null) {
                q = new ConcurrentLinkedQueue<>();
            }
            q.add(delayedRequest);
            return q;
        });

        Runnable cleanup = () -> pendingRequests.computeIfPresent(idChat, (k, q) -> {
            q.remove(delayedRequest);
            return q.isEmpty() ? null : q;
        });

        delayedRequest.getDeferredResult().onCompletion(cleanup);

        return delayedRequest;
    }
}
