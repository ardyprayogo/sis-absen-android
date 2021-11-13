package com.app.absensis.model.level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("level_name")
    @Expose
    private String levelName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_user")
    @Expose
    private Object createdUser;
    @SerializedName("updated_user")
    @Expose
    private Object updatedUser;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Object createdUser) {
        this.createdUser = createdUser;
    }

    public Object getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(Object updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}