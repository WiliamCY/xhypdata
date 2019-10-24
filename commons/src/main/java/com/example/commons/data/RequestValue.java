package com.example.commons.data;

/**
 * @Author skygge.
 * @Date on 2019-08-27.
 * @Github https://github.com/javofxu
 * @Dec: 通用的请求返回
 * @version: ${VERSION}.
 * @Update :
 */
public class RequestValue {
    private long code;
    private String msg;
    private String exe_time;
    public void setCode(long code) {
        this.code = code;
    }
    public long getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setExe_time(String exe_time) {
        this.exe_time = exe_time;
    }
    public String getExe_time() {
        return exe_time;
    }
}
