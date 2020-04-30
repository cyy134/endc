package com.example.serveice.imp;

import com.example.serveice.inser.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisTestServicezImpl implements RedisTestService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String setList() {
        List<String> list1 = new ArrayList<>();
        list1.add("a1");
        list1.add("a2");
        list1.add("a3");

        List<String> list2 = new ArrayList<>();
        list2.add("b1");
        list2.add("b2");
        list2.add("b3");
        //扩展：set(K key,long index, V value)在集合的指定位置插入元素，若指定位置已经有元素，就覆盖，没有就更新，超过集合下标+n会报错
        redisTemplate.opsForList().leftPush("listKey2",list2);
        redisTemplate.opsForList().rightPush("listKey1",list1);
        return "ok";
    }

    @Override
    public List getList(){
//        //获取集合指定位置的值
//        List<String> list = (List) redisTemplate.opsForList().index("listKey1",0);

        //获取指定区间的值
        List<List<String>> list = redisTemplate.opsForList().range("listKey1",0,-1);
//        //移除集合中的右边的元素
//        List list = (List) redisTemplate.opsForList().rightPop("listKey1");
        return list;
    }
}
