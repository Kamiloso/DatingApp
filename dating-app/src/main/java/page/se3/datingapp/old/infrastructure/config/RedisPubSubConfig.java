package page.se3.datingapp.old.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import page.se3.datingapp.old.core.StringKey;
import page.se3.datingapp.old.infrastructure.struct.PubSubInfo;

@Configuration
public class RedisPubSubConfig {
    
    @Bean
    public PubSubInfo pubSubInfo() {
        return new PubSubInfo(StringKey.create("redis-pub-sub"));
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
