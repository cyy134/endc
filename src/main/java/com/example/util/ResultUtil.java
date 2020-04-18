package com.example.util;

public class ResultUtil {

    /**
     * 请求成功
     * @param object
     * @return
     */
    public static Msg success(Object object){
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        return msg;
    }

    /**
     * 请求成功，无数据返回时
     * @return
     */
    public static Msg success(){
        return success(null);
    }

    /**
     * 随机定义code值与msg
     * @param code
     * @param resultInfo
     * @param object
     * @return
     */
    public static Msg success2(Integer code, String resultInfo, Object object){
        Msg msg = new Msg();
        msg.setCode(code);
        msg.setMsg(resultInfo);
        msg.setData(object);
        return msg;
    }


    /**
     * 请求失败
     * @param code
     * @param resultmsg
     * @return
     */
    public static Msg error(Integer code, String resultmsg){
        Msg msg = new Msg();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        return msg;
    }
}
