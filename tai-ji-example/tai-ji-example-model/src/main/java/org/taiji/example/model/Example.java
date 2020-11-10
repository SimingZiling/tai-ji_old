package org.taiji.example.model;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.annotation.Entity;
import org.shaoyin.database.annotation.Table;

import java.util.Date;

@Entity
@Table
public class Example {

    @Column(autoIncrement = true,key = true)
    private int id;

    private String name;

    @Column("add_time")
    private Date addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
