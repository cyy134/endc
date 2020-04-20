package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.model.User;
import com.example.serveice.inser.UserService;
import com.example.util.JSONUtil;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cyy/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getalluser")
    public Msg getAllUser(){
        return ResultUtil.success(userService.getAllUser());
    }

    @RequestMapping(value = "/getuserone",method = RequestMethod.GET)
    public Msg getUserByAcount(String acount){
        User user = userService.getUserInfoByAcount(acount);
        return ResultUtil.success(user);
    }

    @RequestMapping(value = "/getuserinfobycondition")
    public Msg getUserInfoByCondition(@RequestParam Map<String,Object> params){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONUtil.toJSONArray(userService.getUserInfoByCondition(params));
        jsonObject.put("pageIndex",params.get("pageIndex"));
        jsonObject.put("pageSize",params.get("pageSize"));
        jsonObject.put("pageCount","11");//
        jsonObject.put("totalCount","20");//
        jsonObject.put("list",jsonArray);
        return ResultUtil.success(jsonObject);
    }

    @RequestMapping(value = "/insertuser")
    public Msg insertUser(@RequestParam Map<String,Object> params){
        int i = userService.insertStudent(params);
        if(i>0){
            return ResultUtil.success("ok");
        }
        return ResultUtil.error(100,"新增失败");
    }
}
