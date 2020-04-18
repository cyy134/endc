package com.example.serveice.imp;


import com.example.dao.UserMapper;
import com.example.model.User;
import com.example.serveice.inser.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfoByAcount(String acount) {
        User user = userMapper.getUserInfoByAcount(acount);
        return user;
    }

    @Override
    public int insertStudent(Map<String,Object> params) {
        //如果一下信息没有，就添加为默认值
        if(!params.containsKey("passworld")){
            params.put("passworld","123456");
        }if(!params.containsKey("isheadpacticre")){
            params.put("isheadpacticre","0");
        }if(!params.containsKey("createtime")){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("createtime",simpleDateFormat.format(date));
        }
        int i = userMapper.insertStudent(params);
        return i;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public List<User> getUserInfoByCondition(Map<String, Object> params) {
        return userMapper.getUserInfoByCondition(params);
    }
}
