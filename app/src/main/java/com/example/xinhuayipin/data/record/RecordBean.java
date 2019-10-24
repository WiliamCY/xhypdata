/**
  * Copyright 2019 bejson.com 
  */
package com.example.xinhuayipin.data.record;

import java.util.List;

/**
 * Auto-generated: 2019-09-10 11:7:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RecordBean {

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

        private List<Lend_info> lend_info;
        private int lend_count;
        private List<Lend_history_info> lend_history_info;
        private int lend_history_count;
        private List<Reserve_history_info> reserve_history_info;
        private int reserve_history_count;
        private List<Reserve_info> reserve_info;
        private int reserve_count;

        public void setLend_info(List<Lend_info> lend_info) {
            this.lend_info = lend_info;
        }
        public List<Lend_info> getLend_info() {
            return lend_info;
        }

        public void setLend_count(int lend_count) {
            this.lend_count = lend_count;
        }
        public int getLend_count() {
            return lend_count;
        }

        public void setLend_history_info(List<Lend_history_info> lend_history_info) {
            this.lend_history_info = lend_history_info;
        }
        public List<Lend_history_info> getLend_history_info() {
            return lend_history_info;
        }

        public void setLend_history_count(int lend_history_count) {
            this.lend_history_count = lend_history_count;
        }
        public int getLend_history_count() {
            return lend_history_count;
        }

        public void setReserve_history_info(List<Reserve_history_info> reserve_history_info) {
            this.reserve_history_info = reserve_history_info;
        }
        public List<Reserve_history_info> getReserve_history_info() {
            return reserve_history_info;
        }

        public void setReserve_history_count(int reserve_history_count) {
            this.reserve_history_count = reserve_history_count;
        }
        public int getReserve_history_count() {
            return reserve_history_count;
        }

        public List<Reserve_info> getReserve_info() {
            return reserve_info;
        }

        public void setReserve_info(List<Reserve_info> reserve_info) {
            this.reserve_info = reserve_info;
        }

        public int getReserve_count() {
            return reserve_count;
        }

        public void setReserve_count(int reserve_count) {
            this.reserve_count = reserve_count;
        }
    }
}