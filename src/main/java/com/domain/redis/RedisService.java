package com.domain.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis 工具类
 *
 * @author: LJ
 * @create: 2018-11-27
 **/
@Service
public class RedisService {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    private static final long expireTime = 60 * 60 * 24 * 2;


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            // e.printStackTrace()();
        }
        return result;
    }


    /**
     * 写入缓存 (带默认时间）
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean setExpired(final String key, Object value) {
        boolean expireFlag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            expireFlag = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            // e.printStackTrace()();
        }
        return expireFlag;
    }


    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime) {
        boolean expireFlag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            expireFlag = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            // e.printStackTrace()();
        }
        return expireFlag;
    }


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(Set<String> keys) {
        for (String key : keys) {
            remove(key);
        }
    }


    /**
     * 批量删除key
     *
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }


    /**
     * 删除对应的value
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 根据key读取缓存
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }


    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    @SuppressWarnings("unchecked")
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }


    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希删除
     *
     * @param key
     * @param hashKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public void hmDel(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }

    /**
     * 哈希获取entries,注意value为Integer类型,不通用
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Integer> hmEntries(String key) {
        HashOperations<String, String, Integer> hash = redisTemplate.opsForHash();
        return  hash.entries(key);
    }

    /**
     * 哈希获取keys
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<String> hmKeys(String key) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.keys(key);
    }

    /**
     * 根据正则来获取多个key
     *
     * @param pattern
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<String> keys(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        return keys;
    }

    /**
     * 判断缓存中是否有对应的key,操作hash
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean hmExists(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(key,hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    public void leftPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(k, v);
    }

    /**
     * 添加列表集合
     * @param k
     * @param v
     */
    public void lPushAll(String k, List v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPushAll(k, v);
    }

    /**
     * 添加列表集合
     * @param k
     * @param v
     */
    public void leftPushAll(String k, List v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPushAll(k, v);
    }


    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 列表获取数量
     *
     * @param k
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long lsize(String k) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k);
    }


    /**
     * set集合添加
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    @SuppressWarnings("unchecked")
    public void addSet(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }


    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<Object> getSetMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }


    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    @SuppressWarnings("unchecked")
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }


    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }


    /**
     * 从列表中取出一个值
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object lPop(String key) {
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key);
    }


    /**
     * 给Key设置时效时间
     * @param key
     * @param expireTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean setExpireTimeForKey(final String key, Long expireTime) {
        boolean expireFlag = false;
        try {
            expireFlag = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            // e.printStackTrace()();
        }
        return expireFlag;
    }
}
