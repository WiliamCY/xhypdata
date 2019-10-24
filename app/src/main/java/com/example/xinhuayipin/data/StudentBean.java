package com.example.xinhuayipin.data;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-28.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class StudentBean {

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

        private List<Student_finger_info> student_finger_info;

        public void setStudent_info(Student_info student_info) {
            this.student_info = student_info;
        }
        public Student_info getStudent_info() {
            return student_info;
        }


        public void setStudent_finger_info(List<Student_finger_info> student_finger_info) {
            this.student_finger_info = student_finger_info;
        }
        public List<Student_finger_info> getStudent_finger_info() {
            return student_finger_info;
        }

        public class Student_info {

            private int code;
            private String msg;
            private int id;
            private String class_no;
            private String name;
            private String idcard;
            private int status;
            private long school_id;
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

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setClass_no(String class_no) {
                this.class_no = class_no;
            }
            public String getClass_no() {
                return class_no;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }
            public String getIdcard() {
                return idcard;
            }

            public void setStatus(int status) {
                this.status = status;
            }
            public int getStatus() {
                return status;
            }

            public long getSchool_id() {
                return school_id;
            }

            public void setSchool_id(long school_id) {
                this.school_id = school_id;
            }
        }


        public class Student_finger_info {

            private int id;
            private int student_id;
            private long school_id;
            private int fingerprint_id;
            private String fingerprint_data;
            private String fingerprint_pic;
            private long update_time;
            private long create_time;
            private int status;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setStudent_id(int student_id) {
                this.student_id = student_id;
            }
            public int getStudent_id() {
                return student_id;
            }

            public void setSchool_id(long school_id) {
                this.school_id = school_id;
            }
            public long getSchool_id() {
                return school_id;
            }

            public void setFingerprint_id(int fingerprint_id) {
                this.fingerprint_id = fingerprint_id;
            }
            public int getFingerprint_id() {
                return fingerprint_id;
            }

            public void setFingerprint_data(String fingerprint_data) {
                this.fingerprint_data = fingerprint_data;
            }
            public String getFingerprint_data() {
                return fingerprint_data;
            }

            public void setFingerprint_pic(String fingerprint_pic) {
                this.fingerprint_pic = fingerprint_pic;
            }
            public String getFingerprint_pic() {
                return fingerprint_pic;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }
            public long getUpdate_time() {
                return update_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }
            public long getCreate_time() {
                return create_time;
            }

            public void setStatus(int status) {
                this.status = status;
            }
            public int getStatus() {
                return status;
            }

        }
    }
}
