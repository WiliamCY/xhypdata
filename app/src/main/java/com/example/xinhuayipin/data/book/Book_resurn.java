package com.example.xinhuayipin.data.book;

import java.util.List;

public class Book_resurn {

    /**
     * code : 0
     * msg : 操作成功
     * data : [{"id":5,"marc_id":"978-7-5392-9483-4","call_no":"","book_id":"c426af33a0444541","reader_type":2,"reader_id":2,"reader_name":"王毓铭","rule_id":1,"lend_time":1536929533,"lend_bookcase_id":2,"lend_bookcase_no":"0000002","lend_grid_id":2,"lend_grid_no":"1"}]
     * exe_time : 0.006847
     */

    private int code;
    private String msg;
    private String exe_time;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExe_time() {
        return exe_time;
    }

    public void setExe_time(String exe_time) {
        this.exe_time = exe_time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * marc_id : 978-7-5392-9483-4
         * call_no :
         * book_id : c426af33a0444541
         * reader_type : 2
         * reader_id : 2
         * reader_name : 王毓铭
         * rule_id : 1
         * lend_time : 1536929533
         * lend_bookcase_id : 2
         * lend_bookcase_no : 0000002
         * lend_grid_id : 2
         * lend_grid_no : 1
         */

        private int id;
        private String marc_id;
        private String call_no;
        private String book_id;
        private int reader_type;
        private int reader_id;
        private String reader_name;
        private int rule_id;
        private int lend_time;
        private int lend_bookcase_id;
        private String lend_bookcase_no;
        private int lend_grid_id;
        private String lend_grid_no;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMarc_id() {
            return marc_id;
        }

        public void setMarc_id(String marc_id) {
            this.marc_id = marc_id;
        }

        public String getCall_no() {
            return call_no;
        }

        public void setCall_no(String call_no) {
            this.call_no = call_no;
        }

        public String getBook_id() {
            return book_id;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }

        public int getReader_type() {
            return reader_type;
        }

        public void setReader_type(int reader_type) {
            this.reader_type = reader_type;
        }

        public int getReader_id() {
            return reader_id;
        }

        public void setReader_id(int reader_id) {
            this.reader_id = reader_id;
        }

        public String getReader_name() {
            return reader_name;
        }

        public void setReader_name(String reader_name) {
            this.reader_name = reader_name;
        }

        public int getRule_id() {
            return rule_id;
        }

        public void setRule_id(int rule_id) {
            this.rule_id = rule_id;
        }

        public int getLend_time() {
            return lend_time;
        }

        public void setLend_time(int lend_time) {
            this.lend_time = lend_time;
        }

        public int getLend_bookcase_id() {
            return lend_bookcase_id;
        }

        public void setLend_bookcase_id(int lend_bookcase_id) {
            this.lend_bookcase_id = lend_bookcase_id;
        }

        public String getLend_bookcase_no() {
            return lend_bookcase_no;
        }

        public void setLend_bookcase_no(String lend_bookcase_no) {
            this.lend_bookcase_no = lend_bookcase_no;
        }

        public int getLend_grid_id() {
            return lend_grid_id;
        }

        public void setLend_grid_id(int lend_grid_id) {
            this.lend_grid_id = lend_grid_id;
        }

        public String getLend_grid_no() {
            return lend_grid_no;
        }

        public void setLend_grid_no(String lend_grid_no) {
            this.lend_grid_no = lend_grid_no;
        }
    }
}
