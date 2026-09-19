package page.se3.datingapp.onion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.struct.PubSubInfo;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final PubSubInfo pubSubInfo;
    private final StringRedisTemplate redisTemplate;
    private final RedisMessageListenerContainer messageListenerContainer;
    private final JsonSerializer jsonSerializer;

    public <T> void send(String channelName, T obj) {
        String channel = pubSubInfo.stringKey().with(channelName).get();
        String payload = jsonSerializer.serialize(obj);

        redisTemplate.convertAndSend(channel, payload);
    }

    public <T> void subscribe(String channelName, Consumer<T> consumer, Class<T> clazz) {
        String channel = pubSubInfo.stringKey().with(channelName).get();

        MessageListener listener = (message, pattern) -> {
            String payload = new String(message.getBody());
            jsonSerializer.deserialize(payload, clazz).ifPresent(consumer);
        };

        messageListenerContainer.addMessageListener(listener, new ChannelTopic(channel));
    }
}
