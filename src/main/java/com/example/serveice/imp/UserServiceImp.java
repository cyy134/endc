//package com.example.serveice.imp;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.dao.UserMapper;
//import com.example.model.User;
//import com.example.serveice.inser.UserService;
//import com.example.util.PageList;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class UserServiceImp implements UserService {
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Override
//    public User getUserInfoByAcount(String acount) {
//        User user = userMapper.getUserInfoByAcount(acount);
//        return user;
//    }
//
//    @Override
//    public int insertStudent(@Param("params") Map<String,Object> params) {
//        //如果一下信息没有，就添加为默认值
//        if(!params.containsKey("passworld")){
//            params.put("passworld","123456");
//        }if(!params.containsKey("isheadpacticre")){
//            params.put("isheadpacticre","0");
//        }if(!params.containsKey("createtime")){
//            Date date = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            params.put("createtime",simpleDateFormat.format(date));
//        }
//        int i = userMapper.insertStudent(params);
//        return i;
//    }
//
//    @Override
//    public List<User> getAllUser() {
//        return userMapper.getAllUser();
//    }
//
//    @Override
//    public PageList<User> getUserInfoByCondition(JSONObject params) {
//        PageList pageList = new PageList();
//        int pageCount = 0;
//        List<User> list = null;
//        int totalCount = 0;
//        try {
//            int start = (params.getInteger("pageIndex") - 1) * (params.getInteger("pageSize"));
//            params.put("start", start);
//            totalCount = userMapper.getUserInfoCount(params);
//            list = userMapper.getUserInfoByCondition(params);
//            if (totalCount % params.getInteger("pageSize") != 0) {
//                pageCount = totalCount / params.getInteger("pageSize") + 1;
//            } else
//                pageCount = totalCount / params.getInteger("pageSize");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        pageList.setList(list);
//        pageList.setPageCount(pageCount);
//        pageList.setTotalCount(totalCount);
//        pageList.setPageIndex((int)params.get("pageIndex"));
//        pageList.setPageSize((int)params.get("pageSize"));
//        return pageList;
//    }
//
//    @Override
//    public int deleteStudent(List<String> list) {
//        return userMapper.deleteStudent(list);
//    }
//}
