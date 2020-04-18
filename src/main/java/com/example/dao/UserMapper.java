package com.example.dao;

import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {
    User getUserInfoByAcount(String acount);
//    int insertStudent(@Param("acount") String acount, @Param("nickName") String nickName, @Param("emil") String emil);
    int insertStudent(@Param("params") Map<String,Object> params);
    List<User> getAllUser();
    List<User> getUserInfoByCondition(@Param("params") Map<String,Object> params);
}
