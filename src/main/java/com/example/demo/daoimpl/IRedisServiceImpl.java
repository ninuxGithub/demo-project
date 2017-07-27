package com.example.demo.daoimpl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.example.demo.dao.IRedisService;
import com.example.demo.utils.JsonUtil;

@Component
public class IRedisServiceImpl implements IRedisService{

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#set(java.lang.String, java.lang.String)
	 */
    @Override
	public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#set(java.lang.String, java.lang.String, long, java.util.concurrent.TimeUnit)
	 */
    @Override
	public boolean set(final String key, final String value, long timeout, TimeUnit unit) {
        set(key,value);
        boolean result = redisTemplate.expire(key, timeout, unit);
        return result;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#get(java.lang.String)
	 */
    @Override
	public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#expire(java.lang.String, long, java.util.concurrent.TimeUnit)
	 */
    @Override
	public boolean expire(final String key, long expire, TimeUnit unit) {
        return redisTemplate.expire(key, expire, unit);
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#setList(java.lang.String, java.util.List)
	 */
    @Override
	public <T> boolean setList(String key, List<T> list) {
        String value = JsonUtil.writeValueAsString(list);
        return set(key, value);
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#getList(java.lang.String, java.lang.Class)
	 */
    @Override
	public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            return JsonUtil.readStringToList(json, clz);
        }
        return null;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#lpush(java.lang.String, java.lang.Object)
	 */
    @Override
	public long lpush(final String key, Object obj) {
        final String value = JsonUtil.writeValueAsString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#rpush(java.lang.String, java.lang.Object)
	 */
    @Override
	public long rpush(final String key, Object obj) {
        final String value = JsonUtil.writeValueAsString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#lpop(java.lang.String)
	 */
    @Override
	public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }
    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#remove(java.lang.String)
	 */
    @Override
	public void remove(final String key){
        redisTemplate.delete(key);
    }

    /* (non-Javadoc)
	 * @see com.example.demo.daoimpl.IRedisService#keys(java.lang.String)
	 */
    @Override
	public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
