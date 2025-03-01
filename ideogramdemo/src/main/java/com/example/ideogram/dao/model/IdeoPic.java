package com.example.ideogram.dao.model;

import java.util.Date;

public class IdeoPic {
    private String id;

    private String userId;

    private String picLink;

    private String oriPicLink;

    private Date gmtCreate;

    private Date gmtModified;

    private String prompts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getOriPicLink() {
        return oriPicLink;
    }

    public void setOriPicLink(String oriPicLink) {
        this.oriPicLink = oriPicLink;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPrompts() {
        return prompts;
    }

    public void setPrompts(String prompts) {
        this.prompts = prompts;
    }
}