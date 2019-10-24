package com.example.operations.data;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class LoginBean {

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

        private int member_id;
        private int operate_level;
        private String username;
        private long create_time;
        private String user_token;
        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }
        public int getMember_id() {
            return member_id;
        }

        public void setOperate_level(int operate_level) {
            this.operate_level = operate_level;
        }
        public int getOperate_level() {
            return operate_level;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }
        public String getUser_token() {
            return user_token;
        }

    }
    }
