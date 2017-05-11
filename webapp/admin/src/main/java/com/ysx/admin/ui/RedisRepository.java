package com.ysx.admin.ui;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisRepository<K,V extends Redisable<K>>{
	
	@Autowired
	private RedisTemplate<K, V> redisTemplate;
	
	@Value("${spring.redis.expireTime:}")
	private long expireTime;

	@Value("${spring.redis.expireUnit:}")
	private String expireUnit;
	
	@Value("${spring.redis.expireEnabled:}")
	private boolean expireEnabled;
	
	public void save(V v,long time,TimeUnit unit) {
		redisTemplate.opsForValue().set(v.getKey(), v, time, unit);
	}
	
	public void save(V v){
		if(expireEnabled){
			redisTemplate.opsForValue().set(v.getKey(), v, expireTime, TimeUnit.valueOf(expireUnit));
		}else{
			redisTemplate.opsForValue().set(v.getKey(), v);
		}
	}
	
	public void edit(V v,long time,TimeUnit unit) {
		redisTemplate.opsForValue().set(v.getKey(), v, time, unit);
	}
	
	public void edit(V v){
		if(expireEnabled){
			redisTemplate.opsForValue().set(v.getKey(), v, expireTime, TimeUnit.valueOf(expireUnit));
		}
	}
	
	public void del(V v){
		redisTemplate.delete(v.getKey());
	}
	
	public void del(K key){
		redisTemplate.delete(key);
	}
	
	public V query(V v){
		return redisTemplate.opsForValue().get(v.getKey());
	}
	
	public V query(K key){
		return redisTemplate.opsForValue().get(key);
	}
	
}
