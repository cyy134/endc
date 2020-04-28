package com.example.serveice.imp;


import com.alibaba.fastjson.JSONObject;
import com.example.dao.UserMapper;
import com.example.model.User;
import com.example.serveice.inser.UserService;
import com.example.util.PageList;
import org.apache.ibatis.annotations.Param;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 根据acount获取用户信息，并且将其存入redis缓存中
     * @param acount
     * @return
     */
    @Override
    public User getUserInfoByAcount(String acount) {
        String key = "user_"+acount;
        ValueOperations<String,User> operations = redisTemplate.opsForValue();
        Boolean hadKey = redisTemplate.hasKey(key);
        if(hadKey){
            User user = operations.get(key);
            System.out.print("。。。。从缓存中获取数据。。。。。");
            System.out.print(user.getAcount());
            System.out.print("。。。。。。。。。。。。。。。。。");
            return user;
        }else {
            User user = userMapper.getUserInfoByAcount(acount);
            System.out.print("。。。。从数据库中获取数据。。。。。");
            System.out.print(user.getAcount());
            System.out.print("。。。。。。。。。。。。。。。。。");

            /**
             * 设置 key 的值为 value
             * 其它规则与 set(K key, V value)一样
             * @param key 不能为空
             * @param value 设置的值
             * @param timeout 设置过期的时间
             * @param unit 时间单位。不能为空
             */
            operations.set(key,user,5, TimeUnit.HOURS);
            return user;
        }
    }

    @Override
    public int insertStudent(@Param("params") Map<String,Object> params) {
        //如果以下信息没有，就添加为默认值
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
    public PageList<User> getUserInfoByCondition(JSONObject params) {
        PageList pageList = new PageList();
        int pageCount = 0;
        List<User> list = null;
        int totalCount = 0;
        try {
            int start = (params.getInteger("pageIndex") - 1) * (params.getInteger("pageSize"));
            params.put("start", start);
            totalCount = userMapper.getUserInfoCount(params);
            list = userMapper.getUserInfoByCondition(params);
            if (totalCount % params.getInteger("pageSize") != 0) {
                pageCount = totalCount / params.getInteger("pageSize") + 1;
            } else
                pageCount = totalCount / params.getInteger("pageSize");
        }catch (Exception e){
            e.printStackTrace();
        }
        pageList.setList(list);
        pageList.setPageCount(pageCount);
        pageList.setTotalCount(totalCount);
        pageList.setPageIndex((int)params.get("pageIndex"));
        pageList.setPageSize((int)params.get("pageSize"));
        return pageList;
    }

    /**
     * 删除
     * @param list
     * @return
     */
    @Override
    public int deleteStudent(List<String> list) {
        int i = userMapper.deleteStudent(list);
        if(i>0) {
            for (int x = 0; x < list.size(); x++) {
                String key = "user_" + userMapper.getUserInfoByCode(Integer.parseInt(list.get(i))).getAcount();
                boolean haskey = redisTemplate.hasKey(key);
                if(haskey){
                    redisTemplate.delete(key);
                    System.out.print("删除rdis中相对应的用户信息");
                }
            }
        }
        return i;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int changeUserInfo(User user) {
        int i = userMapper.changeUserInfo(user);
        ValueOperations<String,User> operations = redisTemplate.opsForValue();
        if(i>0){
            String key = "user_"+user.getAcount();
            boolean haskey = redisTemplate.hasKey(key);
            if(haskey){
                redisTemplate.delete(key);
                System.out.print("删除缓存中的"+key);
            }
            User usernew = userMapper.getUserInfoByAcount(user.getAcount());
            if(usernew !=null){
                operations.set(key,usernew,5,TimeUnit.HOURS);
            }
        }
        return i;
    }
}
