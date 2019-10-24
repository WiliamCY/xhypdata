/**
  * Copyright 2019 bejson.com 
  */
package com.example.xinhuayipin.data.book;
import java.util.List;

/**
 * Auto-generated: 2019-09-02 10:31:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BookBean {

    private int code;
    private String msg;
    private List<Data> data;
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

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public void setExe_time(String exe_time) {
         this.exe_time = exe_time;
     }
     public String getExe_time() {
         return exe_time;
     }

    public class Data {

        private int id;
        private int bookcase_id;
        private String no;
        private String name;
        private int status;
        private long create_time;
        private long update_time;
        private int operator_id;
        private String operator_name;
        private String book_id;
        private String bookcase_no;
        private Book_info book_info;
        private Marc_info marc_info;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setBookcase_id(int bookcase_id) {
            this.bookcase_id = bookcase_id;
        }
        public int getBookcase_id() {
            return bookcase_id;
        }

        public void setNo(String no) {
            this.no = no;
        }
        public String getNo() {
            return no;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }
        public long getCreate_time() {
            return create_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }
        public long getUpdate_time() {
            return update_time;
        }

        public void setOperator_id(int operator_id) {
            this.operator_id = operator_id;
        }
        public int getOperator_id() {
            return operator_id;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }
        public String getOperator_name() {
            return operator_name;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }
        public String getBook_id() {
            return book_id;
        }

        public void setBookcase_no(String bookcase_no) {
            this.bookcase_no = bookcase_no;
        }
        public String getBookcase_no() {
            return bookcase_no;
        }

        public void setBook_info(Book_info book_info) {
            this.book_info = book_info;
        }
        public Book_info getBook_info() {
            return book_info;
        }

        public void setMarc_info(Marc_info marc_info) {
            this.marc_info = marc_info;
        }
        public Marc_info getMarc_info() {
            return marc_info;
        }

    }
}