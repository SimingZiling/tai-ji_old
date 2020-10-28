package org.shaoyin.database.sql;

import org.shaoyin.database.annotation.Column;
import org.shaoyin.database.annotation.Entity;
import org.shaoyin.database.annotation.Table;
import org.yang.localtools.util.annotation.Alias;

import java.util.Date;

@Entity
@Table("user_s")
public class User {

    @Column(notNull = true)
    public int id;

    @Column
    private String name;

    @Column
    private Integer a;

    @Column
    private int b;

    @Column
    private boolean c;

    @Column
    private Boolean d;

    private Date e;



}
