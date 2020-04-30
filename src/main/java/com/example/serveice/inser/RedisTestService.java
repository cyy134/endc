package com.example.serveice.inser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisTestService {

    String setList();
    List getList();

}
