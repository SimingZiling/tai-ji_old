package org.shaoyin.database.sql;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.annotation.Entity;
import org.shaoyin.database.annotation.Table;
import org.yang.localtools.util.annotation.Alias;

import java.util.Date;

@Entity
@Table("user_s")
public class User {

    @Column(value = "id_s",key = true)
//    @Alias("aa")
    private Integer id;

    private String name;

    private String email;

    private String a;

    public Date birthday;

    private User user;

    @Column(length = 10,decimals = 2)
    private Float ints;

    private String sha;

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

    @Column(value = "email_s")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", ints=" + ints +
                ", sha='" + sha + '\'' +
                '}';
    }
}
