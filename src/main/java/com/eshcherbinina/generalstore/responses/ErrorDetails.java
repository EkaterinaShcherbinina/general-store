package com.eshcherbinina.generalstore.responses;

import lombok.Builder;

import java.util.Date;

@Builder
public class ErrorDetails {
    private Date timestamp;
    private String title;
    private String type;
    private int status;
    private String detail;
    private String path;

    public ErrorDetails(Date timestamp, String title, String type, int status, String detail, String path) {
        this.timestamp = timestamp;
        this.title = title;
        this.type = type;
        this.status = status;
        this.detail = detail;
        this.path = path;
    }

    public ErrorDetails() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
