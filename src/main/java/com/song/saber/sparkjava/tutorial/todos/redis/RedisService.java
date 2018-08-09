package com.sparkjava.tutorial.todos.redis;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Redis存储服务
 * 
 * @author zhouzhichao
 *
 * @param <K>
 * @param <V>
 */
@Service("redisService")
public class RedisService<K, V> {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<K, V> redisTemplate;

	/**
	 * 
	 * @author Jacky
	 * @param key
	 * @param value
	 */
	public void put(final K key, final V value, long timeout, TimeUnit unit) {
		ValueOperations<K, V> ops = redisTemplate.opsForValue();
		ops.set(key, value, timeout, unit);
	}

	public Set<K> keys(K pattern){
		return redisTemplate.keys(pattern);
	}


	/**
	 * 
	 * zAdd(向Key这个set添加元素，按照时间戳排序，timeout时间后超时) @Title zAdd @author
	 * xin.song @return void 返回类型 @throws
	 */
	public void zAdd(final K key, final V value, long timeout, TimeUnit unit) {
		ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
		ops.add(key, value, System.currentTimeMillis());
		ops.getOperations().expire(key, timeout, unit);
	}

	public Set<V> zGetByScore(K key, long start, long end) {
		ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
		Set<V> values = ops.rangeByScore(key, start, end);
		return values;
	}

	public Long zRMRange(final K key, long start, long end) {
		ZSetOperations<K, V> ops = redisTemplate.opsForZSet();
		return ops.removeRange(key, start, end);
	}
	
	/**
	 * 
	 * @author Jacky
	 * @param key
	 * @param value
	 */
	public void put(final K key, final V value) {
		ValueOperations<K, V> ops = redisTemplate.opsForValue();
		ops.set(key, value);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public V getSet(final K key, final V value) {
		return redisTemplate.execute(new RedisCallback<V>() {
			@SuppressWarnings("unchecked")
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
				byte[] keys = keySerializer.serialize(key);
				RedisSerializer<V> valueSerializer = (RedisSerializer<V>) redisTemplate.getValueSerializer();
				byte[] values = valueSerializer.serialize(value);
				byte[] old = connection.getSet(keys, values);
				return valueSerializer.deserialize(old);
			}
		});
	}

	/**
	 * 
	 * @author Jacky
	 * @return
	 */
	public RedisConnectionFactory getRedisConnectionFactory() {
		return redisTemplate.getConnectionFactory();
	}

	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	public V get(K key) {
		if (key == null) {
			return null;
		}
		ValueOperations<K, V> ops = redisTemplate.opsForValue();
		return ops.get(key);
	}

	public boolean expire(final K key, final long timeout, final TimeUnit unit) {
		if (key == null) {
			return false;
		}
		return redisTemplate.expire(key, timeout, unit);
	}

	public boolean expireAt(final K key, final Date date) {
		if (key == null) {
			return false;
		}
		return redisTemplate.expireAt(key, date);
	}

	public Boolean hasKey(final K key) {
		return redisTemplate.hasKey(key);
	}

	public void remove(final K key) {
		if (key != null) {
			redisTemplate.delete(key);
		}
	}

	public long increment(K key, long delta) {
		ValueOperations<K, V> ops = redisTemplate.opsForValue();
		return ops.increment(key, delta);
	}

	public long getIncrValue(final String key) {

		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] rowkey = serializer.serialize(key);
				byte[] rowval = connection.get(rowkey);
				try {
					String val = serializer.deserialize(rowval);
					return Long.parseLong(val);
				} catch (Exception e) {
					return 0L;
				}
			}
		});
	}

	public boolean setNX(final K key, final V value) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@SuppressWarnings("unchecked")
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
				byte[] keys = keySerializer.serialize(key);
				RedisSerializer<V> valueSerializer = (RedisSerializer<V>) redisTemplate.getValueSerializer();
				byte[] values = valueSerializer.serialize(value);
				return connection.setNX(keys, values);
			}
		});
	}

	/**
	 * 
	 * tryLock(在timeout时间内不断尝试获取锁，成功返回true，失败返回false) @Title tryLock @author
	 * xin.song @return boolean 返回类型 @throws
	 * 
	 *
	 */
	public boolean tryLock(K key, V value, final long timeout, final long expire, TimeUnit unit) {
		long nano = System.nanoTime();
		do {
			boolean result = setNX(key, value);
			if (result) {
				// 获取锁成功，设置锁超时时间
				redisTemplate.expire(key, expire, TimeUnit.SECONDS);
				return true;
			}
			if (timeout == 0) {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
		return false;
	}

	public boolean lock(K key, V value, final long expire, TimeUnit unit) {
		boolean result = setNX(key, value);
		if (result) {
			redisTemplate.expire(key, expire, unit);
		}
		return result;
	}

	public void unLock(K key) {
		if (key != null) {
			redisTemplate.delete(key);
		}
	}
	public void hmput(K key, Map<String,Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}


	public void hput(K key, Object hashKey, V value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	@SuppressWarnings("unchecked")
	public V hget(K key, Object hashKey) {
		return (V) redisTemplate.opsForHash().get(key, hashKey);
	}

	public Long hDel(K key, Object hashKey) {
		return redisTemplate.opsForHash().delete(key, hashKey);
	}

	public Map<Object, Object> hEntry(K key) {
		return redisTemplate.opsForHash().entries(key);
	}

	public Long leftPush(K key, V value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	public Long rightPush(K key, V value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	public V rightPop(K key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	// 获取start到end之间的元素
	public List<V> range(K key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	public Long lRemove(K key, long count, V value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}

	// 删除start和end之外的元素
	public void trimList(K key, long start, long end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	// 获取list长度
	public Long listSize(K key) {
		return redisTemplate.opsForList().size(key);
	}

	public Long setSize(K key) {
		return redisTemplate.opsForSet().size(key);
	}

	public List<V> pipeline(final List<K> keys) {
		RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) redisTemplate.getValueSerializer();

		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for (K k : keys) {
					final byte[] rawKey = keySerializer.serialize(k);
					connection.get(rawKey);
				}
				return connection.closePipeline();
			}
		};

		List<Object> list = redisTemplate.execute(pipelineCallback);
		List<V> resultList = new ArrayList<>();
		for (Object obj : list) {
			byte[] rawVal = (byte[]) obj;
			V v = valueSerializer.deserialize(rawVal);
			resultList.add(v);
		}
		return resultList;
	}

}
