package com.ashokvarma.gander.internal.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 02/06/18
 */
@Entity(tableName = "HttpTransaction")
public class HttpTransaction {

    private static final SimpleDateFormat TIME_ONLY_FMT = new SimpleDateFormat("HH:mm:ss", Locale.US);
    ///////////////////////////////////////////////////////////////////////////
    // for UI not related to model.
    ///////////////////////////////////////////////////////////////////////////
    @Ignore
    public String searchKey;
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "request_date")
    private Date requestDate;
    @ColumnInfo(name = "tag")
    private String method;
    @ColumnInfo(name = "priority")
    private int priority;
    @ColumnInfo(name = "request_content_length")
    private Long requestContentLength;
    @ColumnInfo(name = "request_body", typeAffinity = ColumnInfo.TEXT)
    private String requestBody;
    @ColumnInfo(name = "error", typeAffinity = ColumnInfo.TEXT)
    private String error;

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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getRequestContentLength() {
        return requestContentLength;
    }

    public void setRequestContentLength(Long requestContentLength) {
        this.requestContentLength = requestContentLength;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}