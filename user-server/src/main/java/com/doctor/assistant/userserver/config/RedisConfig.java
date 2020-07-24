//package com.doctor.assistant.userserver.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
//import java.lang.reflect.Method;
//import java.time.Duration;
//
//@Configuration
//@EnableCaching
//@AutoConfigureAfter(RedisAutoConfiguration.class)
//public class RedisConfig extends CachingConfigurerSupport {
//
//    /**
//     * 缓存对象集合中，缓存是以 key-value 形式保存的。当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key。
//     * @return
//     */
//    @Bean
//    public KeyGenerator wiselyKeyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//
//    }
//
//
////    @Bean
////    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
////        RedisCacheManager rm = new RedisCacheManager(redisTemplate);
////        rm.setDefaultExpiration(30l);// 设置缓存时间
////        return rm;
////    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
////        RedisCacheManager cacheManager = RedisCacheManager.create(factory);
////        return cacheManager;
//        //初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
//        //设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
//        //ClassLoader loader = this.getClass().getClassLoader();
//        //JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
//        //RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
//        //RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
//        //设置默认超过期时间是30秒
//        defaultCacheConfig.entryTtl(Duration.ofSeconds(10));
//        //初始化RedisCacheManager
//        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
//        return cacheManager;
//    }
//
//
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        @SuppressWarnings({ "rawtypes", "unchecked" })
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//}
