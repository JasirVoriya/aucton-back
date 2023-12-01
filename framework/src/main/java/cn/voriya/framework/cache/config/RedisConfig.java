package cn.voriya.framework.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public <K,V>  RedisTemplate<K, V> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 设置Key的序列化器
        template.setKeySerializer(new StringRedisSerializer());

        // 设置Hash Key的序列化器
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置Value的序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置Hash Value的序列化器
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}

