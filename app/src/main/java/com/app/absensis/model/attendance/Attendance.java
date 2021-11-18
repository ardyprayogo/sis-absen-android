package com.app.absensis.model.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("work_hour")
    @Expose
    private String workHour;
    @SerializedName("late_check_out")
    @Expose
    private String lateCheckOut;
    @SerializedName("late_check_in")
    @Expose
    private String lateCheckIn;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getWorkHour() {
        return workHour;
    }

    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }

    public String getLateCheckOut() {
        return lateCheckOut;
    }

    public void setLateCheckOut(String lateCheckOut) {
        this.lateCheckOut = lateCheckOut;
    }

    public String getLateCheckIn() {
        return lateCheckIn;
    }

    public void setLateCheckIn(String lateCheckIn) {
        this.lateCheckIn = lateCheckIn;
    }

    public boolean isLate() {
        try {
            String lateCi = lateCheckIn.substring(0, 1);
            if (lateCi.equals("-"))
                return false;
            else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
