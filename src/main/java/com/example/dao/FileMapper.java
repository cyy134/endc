package com.example.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface FileMapper {

    int addUser(@Param("params") Map<String,Object> params);
}
