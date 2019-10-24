package com.example.xinhuayipin.data;

/**
 * @Author skygge.
 * @Date on 2019-08-27.
 * @Github https://github.com/javofxu
 * @Dec: 初始化Token
 * @version: ${VERSION}.
 * @Update :
 */
public class TokenBean {

    private int code;
    private String msg;
    private Data data;
    private String exe_time;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setExe_time(String exe_time) {
        this.exe_time = exe_time;
    }
    public String getExe_time() {
        return exe_time;
    }

    public class Data {

        private String token;
        private String ip;
        private String content;
        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
        public String getIp() {
            return ip;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

    }
}
