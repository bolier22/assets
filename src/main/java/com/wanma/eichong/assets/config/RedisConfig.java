package com.wanma.eichong.assets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 解决redis插入中文乱码
     *
     * @return
     */
    @Bean
    public RedisTemplate redisTemplateInit() {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

//    @Bean
//    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        return redisTemplate;
//    }
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(){
//        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
//    }

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     * @param redisConnectionFactory
     * @return
     */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        // 设置value的序列化规则和 key的序列化规则
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

//    @Bean
//    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        return redisTemplate;
//    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置有效时间的redis数据，时间单位：秒
     */
    public void setValidTimeStr(String key, String value, long validTime) {
        redisTemplate.opsForValue().set(key, value, validTime, TimeUnit.SECONDS);
    }

    public void setTime(String key, Long num, TimeUnit time){
        redisTemplate.expire(key, num, time);
    }
    /**
     * String Get
     */
    public String strGet(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * String Get
     */
    public Object strGetobject(String key){
        return  redisTemplate.opsForValue().get(key);
    }

    /**
     * String Set
     */
    public void strSet(String key,Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * delete
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
    /**
     * list leftPush
     */
    public void listLeftPush(String key, Object value){
        redisTemplate.opsForList().leftPush(key,value);
    }
    /**
     * hash Set
     */
    public void hashPutAll(String key, Map<String,String> value){
        redisTemplate.opsForHash().putAll(key,value);
    }
    /**
     * hash get
     */
    public Map<String,String> hashGetAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }
    /**
     * hash delete
     */
    public void hashDelete(String key, Object... hashKey){
        redisTemplate.opsForHash().delete(key,hashKey);
    }
    /**
     * 根据redisKey 和 list 中的value 删除redis中的value
     */
    public void removeListValue(List<String> valueList, String keyName){
        valueList.forEach(value-> redisTemplate.opsForList().remove(keyName,1,value));
    }
    /**
     * list GetAll
     */
    public List<String> listGetAll(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }


    /**
     * list SetAll
     */
    public void listSetAllOrderPk(String key, ArrayList<Long> newList){
        redisTemplate.opsForList().leftPushAll(key,newList);
    }



    /**
     * set add
     */
    public void setAdd(String key,String value){
        redisTemplate.opsForSet().add(key,value);
    }

    /**
     * set remove
     */
    public void setRemove(String key,String value){
        redisTemplate.opsForSet().remove(key,value);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public boolean setNx(String key, String value, long validTime) {
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (result) {
            return redisTemplate.expire(key, validTime, TimeUnit.SECONDS);
        }
        return false;
    }

    public boolean setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
}
