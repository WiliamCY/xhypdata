/**
  * Copyright 2019 bejson.com 
  */
package com.example.xinhuayipin.data.fetch;

/**
 * Auto-generated: 2019-08-30 16:15:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FetchBean{

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

        private Student_info student_info;
        private Book_reserve_info book_reserve_info;
        private Lend_info lend_info;
        private Rule_info rule_info;

        public Student_info getStudent_info() {
            return student_info;
        }

        public void setStudent_info(Student_info student_info) {
            this.student_info = student_info;
        }

        public Book_reserve_info getBook_reserve_info() {
            return book_reserve_info;
        }

        public void setBook_reserve_info(Book_reserve_info book_reserve_info) {
            this.book_reserve_info = book_reserve_info;
        }

        public Lend_info getLend_info() {
            return lend_info;
        }

        public void setLend_info(Lend_info lend_info) {
            this.lend_info = lend_info;
        }

        public Rule_info getRule_info() {
            return rule_info;
        }

        public void setRule_info(Rule_info rule_info) {
            this.rule_info = rule_info;
        }
    }

}