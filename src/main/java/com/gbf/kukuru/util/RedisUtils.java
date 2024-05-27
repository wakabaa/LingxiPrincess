package com.gbf.kukuru.util;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author ginoko
 * @since 2022-06-10
 */
public class RedisUtils {
    /**
     * 全局Redis连接对象
     */
    private static final StringRedisTemplate redis;

    static {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        factory.afterPropertiesSet();
        redis = new StringRedisTemplate(factory);
    }

    /**
     * 获取指定键对应的值
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return redis.opsForValue().get(key);
    }

    /**
     * 存储键值对
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        redis.opsForValue().set(key, value);
    }

    /**
     * 存储键值对(有过期时间)
     *
     * @param key        键
     * @param value      值
     * @param expireTime 多少时间后过期(毫秒)
     */
    public static void setEx(String key, String value, long expireTime) {
        redis.opsForValue().set(key, value, expireTime, TimeUnit.MILLISECONDS);
    }
}
