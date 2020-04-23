package com.example.dao;

import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
@Mapper
public interface FileMapper {

    int addUser(@Param("user") User user);
}
