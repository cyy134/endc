package com.example.controller;

import com.example.serveice.inser.RedisTestService;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cyy/redisTest")
public class RedisTestController {

    @Autowired
    RedisTestService redisTestService;

    @RequestMapping("/setList")
    public Msg setList(){
        return ResultUtil.success(redisTestService.setList());
    }

    @RequestMapping("/getList")
    public Msg getList(){
        return ResultUtil.success(redisTestService);
    }
}
