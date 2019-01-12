package com.ashokvarma.gander.internal.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "HttpTransaction")
public class HttpTransaction {

    @Ignore
    public String searchKey;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "tag")
    private String tag;
    @ColumnInfo(name = "priority")
    private int priority;
    @ColumnInfo(name = "length")
    private int length;
    @ColumnInfo(name = "body", typeAffinity = ColumnInfo.TEXT)
    private String body;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int length() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String body() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}