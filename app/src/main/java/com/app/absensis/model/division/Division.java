package com.app.absensis.model.division;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Division {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("division_name")
    @Expose
    private String divisionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
