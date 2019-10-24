package com.example.operations.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author skygge.
 * @Date on 2019-09-20.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */

@Entity
public class DeviceBean {
    @Id(autoincrement = true)
    Long id;
    String name;
    String machid;
    String door_count;
    @Generated(hash = 1439023668)
    public DeviceBean(Long id, String name, String machid, String door_count) {
        this.id = id;
        this.name = name;
        this.machid = machid;
        this.door_count = door_count;
    }
    @Generated(hash = 74682814)
    public DeviceBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMachid() {
        return this.machid;
    }
    public void setMachid(String machid) {
        this.machid = machid;
    }
    public String getDoor_count() {
        return this.door_count;
    }
    public void setDoor_count(String door_count) {
        this.door_count = door_count;
    }
}
