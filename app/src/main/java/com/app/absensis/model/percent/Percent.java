package com.app.absensis.model.percent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Percent {

    @SerializedName("attendance")
    @Expose
    private int attendance;
    @SerializedName("work")
    @Expose
    private int work;
    @SerializedName("percentation")
    @Expose
    private String percentation;

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public String getPercentation() {
        return percentation;
    }

    public void setPercentation(String percentation) {
        this.percentation = percentation;
    }

}