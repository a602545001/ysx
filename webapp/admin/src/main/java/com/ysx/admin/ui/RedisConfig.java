package com.ysx.admin.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

//@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplateUser(RedisConnectionFactory factory) {
        RedisTemplate<K, V> template = new RedisTemplate<K, V>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }
    
    @Bean
    public <K, V extends Redisable<K>> RedisRepository<K, V> redisRepository(){
    	RedisRepository<K, V> redisRepository=new RedisRepository<K, V>();
    	return redisRepository;
    }
}