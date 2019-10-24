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
public class Book_reserve_info implements Serializable {

    private int count;
    private List<Data> data;
    public void setCount(int count) {
         this.count = count;
     }
     public int getCount() {
         return count;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public class Data {

        private int id;
        private int bookcase_id;
        private String bookcase_no;
        private int grid_id;
        private String grid_no;
        private String book_id;
        private int reader_type;
        private int reader_id;
        private String reader_name;
        private int rule_id;
        private int reservation_person_id;
        private String reservation_person_name;
        private long create_time;
        private long update_time;
        private long expiry_time;
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

        public void setBookcase_no(String bookcase_no) {
            this.bookcase_no = bookcase_no;
        }
        public String getBookcase_no() {
            return bookcase_no;
        }

        public void setGrid_id(int grid_id) {
            this.grid_id = grid_id;
        }
        public int getGrid_id() {
            return grid_id;
        }

        public void setGrid_no(String grid_no) {
            this.grid_no = grid_no;
        }
        public String getGrid_no() {
            return grid_no;
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

        public void setReservation_person_id(int reservation_person_id) {
            this.reservation_person_id = reservation_person_id;
        }
        public int getReservation_person_id() {
            return reservation_person_id;
        }

        public void setReservation_person_name(String reservation_person_name) {
            this.reservation_person_name = reservation_person_name;
        }
        public String getReservation_person_name() {
            return reservation_person_name;
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

        public void setExpiry_time(long expiry_time) {
            this.expiry_time = expiry_time;
        }
        public long getExpiry_time() {
            return expiry_time;
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