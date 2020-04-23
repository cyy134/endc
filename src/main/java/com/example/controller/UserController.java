package com.example.controller;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 按照条件查找
     * @param acount
     * @param pageIndex
     * @param pageSize
     * @param nickName
     * @param age
     * @param emil
     * @param createtime
     * @param ssex
     * @param phone
     * @return
     */
    @RequestMapping(value = "/getuserinfobycondition")
    public Msg getUserInfoByCondition(String acount, int pageIndex, int pageSize,String nickName,String age,String emil,
                                      String createtime,String ssex,String phone){
        JSONObject params = new JSONObject();
        params.put("acount",acount);
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        params.put("nickName",nickName);
        params.put("age",age);
        params.put("emil",emil);
        params.put("createtime",createtime);
        params.put("ssex",ssex);
        params.put("phone",phone);
        JSONObject result = JSONUtil.objectToJSONObject(userService.getUserInfoByCondition(params));
        return ResultUtil.success(result);
    }

    /**
     * 增加
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertuser")
    public Msg insertUser(@RequestParam Map<String,Object> params){
        int i = userService.insertStudent(params);
        if(i>0){
            return ResultUtil.success("ok");
        }
        return ResultUtil.error(100,"新增失败");
    }

    /**
     * 删除（批量或者单个）
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteStudent")
    public Msg deleteStudent(String ids){
        List<String> idList = new ArrayList<>();
        String[] strs = ids.split(",");
        for (String str:strs){
            idList.add(str);
        }
        int result = userService.deleteStudent(idList);
        if(result>0){
            return ResultUtil.success("ok");
        }
        return ResultUtil.error(100,"失败");

    }
}
