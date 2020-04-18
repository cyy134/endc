package com.example.controller;

import com.example.model.User;
import com.example.serveice.inser.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cyy/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getalluser")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @RequestMapping(value = "/getuserone",method = RequestMethod.GET)
    public User getUserByAcount(String acount){
        User user = userService.getUserInfoByAcount(acount);
        return user;
    }

    @RequestMapping(value = "/getuserinfobycondition")
    public List<User> getUserInfoByCondition(@RequestParam Map<String,Object> params){
        return userService.getUserInfoByCondition(params);
    }

//    @RequestMapping(value = "/insertuser")
//    public int insertUser(String acount,String nickName,String emil){
//        int i = userService.insertStudent(acount,nickName,emil);
//        return i;
//    }

    @RequestMapping(value = "/insertuser")
    public int insertUser(@RequestParam Map<String,Object> params){
        int i = userService.insertStudent(params);
        return i;
    }
}
