package com.example.serveice.inser;

import com.example.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserInfoByAcount(String acount);
//    int insertStudent(String acount,String nickName,String emil);
    int insertStudent(Map<String,Object> params);
    List<User> getAllUser();
    List<User> getUserInfoByCondition(Map<String,Object> params);
}
