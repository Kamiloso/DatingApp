package page.se3.datingapp.infrastructure.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.interfaces.DistributedEventSystem;
import page.se3.datingapp.domain.core.tool.UniqueKey;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class RedisEventSystem implements DistributedEventSystem {

    private static final UniqueKey REDIS_EVENT_KEY = UniqueKey.register("redis-event");

    private final StringRedisTemplate redisTemplate;
    private final RedisMessageListenerContainer messageListenerContainer;
    private final JsonSerializer jsonSerializer;

    public <T> void invoke(String channelName, T obj) {
        String channel = REDIS_EVENT_KEY.get(channelName);
        String payload = jsonSerializer.serialize(obj);

        redisTemplate.convertAndSend(channel, payload);
    }

    public <T> void subscribe(String channelName, Consumer<T> consumer, Class<T> clazz) {
        String channel = REDIS_EVENT_KEY.get(channelName);

        MessageListener listener = (message, pattern) -> {
            String payload = new String(message.getBody());
            jsonSerializer.deserialize(payload, clazz).ifPresent(consumer);
        };

        messageListenerContainer.addMessageListener(listener, new ChannelTopic(channel));
    }
}
