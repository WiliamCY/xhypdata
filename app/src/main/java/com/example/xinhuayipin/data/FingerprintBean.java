package com.example.xinhuayipin.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author skygge.
 * @Date on 2019-08-20.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
@Entity
public class FingerprintBean {
    @Id
    Long id;
    long student_id;
    long school_id;
    int fingerprint_id;
    String fingerprint_data;
    String fingerprint_pic;
    long update_time;
    long create_time;
    int status;
    @Generated(hash = 1427159938)
    public FingerprintBean(Long id, long student_id, long school_id,
            int fingerprint_id, String fingerprint_data, String fingerprint_pic,
            long update_time, long create_time, int status) {
        this.id = id;
        this.student_id = student_id;
        this.school_id = school_id;
        this.fingerprint_id = fingerprint_id;
        this.fingerprint_data = fingerprint_data;
        this.fingerprint_pic = fingerprint_pic;
        this.update_time = update_time;
        this.create_time = create_time;
        this.status = status;
    }
    @Generated(hash = 1309389135)
    public FingerprintBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getStudent_id() {
        return this.student_id;
    }
    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }
    public long getSchool_id() {
        return this.school_id;
    }
    public void setSchool_id(long school_id) {
        this.school_id = school_id;
    }
    public int getFingerprint_id() {
        return this.fingerprint_id;
    }
    public void setFingerprint_id(int fingerprint_id) {
        this.fingerprint_id = fingerprint_id;
    }
    public String getFingerprint_data() {
        return this.fingerprint_data;
    }
    public void setFingerprint_data(String fingerprint_data) {
        this.fingerprint_data = fingerprint_data;
    }
    public String getFingerprint_pic() {
        return this.fingerprint_pic;
    }
    public void setFingerprint_pic(String fingerprint_pic) {
        this.fingerprint_pic = fingerprint_pic;
    }
    public long getUpdate_time() {
        return this.update_time;
    }
    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }
    public long getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    
    
}
