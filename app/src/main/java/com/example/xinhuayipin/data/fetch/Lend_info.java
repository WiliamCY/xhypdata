/**
  * Copyright 2019 bejson.com 
  */
package com.example.xinhuayipin.data.fetch;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2019-08-30 16:15:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Lend_info implements Serializable {

    private int count;
    private List<Data> data;
    public void setCount(int count) {
         this.count = count;
     }
     public int getCount() {
         return count;
     }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        private int id;
        private String marc_id;
        private String call_no;
        private String book_id;
        private int reader_type;
        private int reader_id;
        private String reader_name;
        private int rule_id;
        private long lend_time;
        private int lend_bookcase_id;
        private String lend_bookcase_no;
        private int lend_grid_id;
        private String lend_grid_no;
        private Book_info book_info;
        private Marc_info marc_info;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setMarc_id(String marc_id) {
            this.marc_id = marc_id;
        }
        public String getMarc_id() {
            return marc_id;
        }

        public void setCall_no(String call_no) {
            this.call_no = call_no;
        }
        public String getCall_no() {
            return call_no;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }
        public String getBook_id() {
            return book_id;
        }

        public void setReader_type(int reader_type) {
            this.reader_type = reader_type;
        }
        public int getReader_type() {
            return reader_type;
        }

        public void setReader_id(int reader_id) {
            this.reader_id = reader_id;
        }
        public int getReader_id() {
            return reader_id;
        }

        public void setReader_name(String reader_name) {
            this.reader_name = reader_name;
        }
        public String getReader_name() {
            return reader_name;
        }

        public void setRule_id(int rule_id) {
            this.rule_id = rule_id;
        }
        public int getRule_id() {
            return rule_id;
        }

        public void setLend_time(long lend_time) {
            this.lend_time = lend_time;
        }
        public long getLend_time() {
            return lend_time;
        }

        public void setLend_bookcase_id(int lend_bookcase_id) {
            this.lend_bookcase_id = lend_bookcase_id;
        }
        public int getLend_bookcase_id() {
            return lend_bookcase_id;
        }

        public void setLend_bookcase_no(String lend_bookcase_no) {
            this.lend_bookcase_no = lend_bookcase_no;
        }
        public String getLend_bookcase_no() {
            return lend_bookcase_no;
        }

        public void setLend_grid_id(int lend_grid_id) {
            this.lend_grid_id = lend_grid_id;
        }
        public int getLend_grid_id() {
            return lend_grid_id;
        }

        public void setLend_grid_no(String lend_grid_no) {
            this.lend_grid_no = lend_grid_no;
        }
        public String getLend_grid_no() {
            return lend_grid_no;
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