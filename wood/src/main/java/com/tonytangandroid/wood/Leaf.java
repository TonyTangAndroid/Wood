package com.tonytangandroid.wood;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Leaf")
class Leaf {

    @Ignore
    public String searchKey;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "createAt")
    private long createAt;
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

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
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