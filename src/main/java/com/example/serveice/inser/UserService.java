package com.example.serveice.inser;

import com.alibaba.fastjson.JSONObject;
import com.example.model.User;
import com.example.util.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserInfoByAcount(String acount);
//    int insertStudent(String acount,String nickName,String emil);
    int insertStudent(@Param("params") Map<String,Object> params);
    List<User> getAllUser();
    PageList<User> getUserInfoByCondition(JSONObject params);
    int deleteStudent(List<String> list);
}
