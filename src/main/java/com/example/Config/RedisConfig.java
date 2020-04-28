package com.example.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 新建restTemplate交个spring容器管理
     * 注意：
     * springboot其实已经自动注入了RedisTemplate,
     * 但是泛型是RedisTemplate<Object,Object>，而且没有指定序列化方式
     * 而我们需要key都是string类型的 ，避免频繁类型转换，所以重写
     * 当手动去注入一个RedisTemplate后 springboot将不会自动注入
     *RedisConnectionFactory:springboot自动读取配置文件然后注入
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置连接工厂，  工厂用于创建连接       template.setConnectionFactory(factory);
        //设置自定义序列化方式      setSerializeConfig(template,factory );
        return template;
    }

    /**
     * 为什么要序列化？
     *序列化能加快网络传输，而且redis的value都是字符串，
     * 我们希望value放入任意的数据，就需要手动去序列化为字符串，这儿直接配置了，
     * 以后传入任意value的时候，就会自动序列化
     *
     * 大家如果不能理解下面的配置，只需要记住是为了以后设置任意值的时候方便就可以了，以后再说
     * 比如:我们现在想保存一个student对象到redis，但是redis只能存字符串，那么我们需要把对象通过json工具类转换为字符串
     * 再去存入，写了这个就不需要转了，传入student对象，内部自动帮助我们转换为字符串
     */
    private void setSerializeConfig(RedisTemplate<String,Object> redisTemplate ,RedisConnectionFactory redisConnectionFactory){
        //设置链接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //对字符串采取普通的序列化方式 适用于key 因为我们一般采取简单字符串作为key
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //普通的string类型的key采用 普通序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //普通hash类型的key也使用 普通序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //解决查询缓存转换异常的问题  大家不能理解就直接用就可以了 这是springboot自带的jackson序列化类，但是会有一定问题
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //普通的值采用jackson方式自动序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //hash类型的值也采用jackson方式序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //属性设置完成afterPropertiesSet就会被调用，可以对设置不成功的做一些默认处理
        redisTemplate.afterPropertiesSet();

    }
}
