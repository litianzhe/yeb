package com.xxxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @className: RedisConfig
 * @copyright: HTD
 * @description: Redis序列化配置类
 * <功能详细描述>
 * @author: 里天者
 * @date: 2022/2/22
 * @version: 1.0
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory){

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// String 类型 key 序列器
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// String 类型 value 序列器
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// Hash 类型 key 序列器
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// Hash 类型 value 序列器
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
}
