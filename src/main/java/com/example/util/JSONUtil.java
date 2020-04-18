package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class JSONUtil {
    /**
     * 对象转JSONObject
     * @param object
     * @return
     */
    public static JSONObject objectToJSONObject(Object object){
        String jsonStr = JSONObject.toJSONString(object);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject;
    }

    /**
     * list转JSONArray
     * @param list
     * @return
     */
    public static JSONArray toJSONArray(List list){
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
        return array;
    }
}
