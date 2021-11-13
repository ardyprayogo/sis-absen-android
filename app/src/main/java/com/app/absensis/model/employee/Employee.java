package com.app.absensis.model.employee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_address")
    @Expose
    private String employeeAddress;
    @SerializedName("employee_phone")
    @Expose
    private String employeePhone;
    @SerializedName("employee_email")
    @Expose
    private String employeeEmail;
    @SerializedName("level_id")
    @Expose
    private int levelId;
    @SerializedName("division_id")
    @Expose
    private int divisionId;
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
    @SerializedName("level_name")
    @Expose
    private String levelName;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("password")
    @Expose
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
