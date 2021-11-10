package com.app.absensis.constant;

import com.app.absensis.R;
import com.app.absensis.ui.menu.MenuModel;

import java.util.ArrayList;

public class MenuConst {
    public final static String CHECK_IN = "Check In";
    public final static String CHECK_OUT = "Check Out";
    public final static String HISTORY = "History Absensi";
    public final static String EMPLOYEE = "Karyawan";
    public final static String DIVISION = "Divisi";
    public final static String LEVEL = "Jabatan";

    public static ArrayList<MenuModel> getHomeMenu() {
        ArrayList<MenuModel> menuModels = new ArrayList<>();
        menuModels.add(new MenuModel(CHECK_IN, R.drawable.ic_home_checkin));
        menuModels.add(new MenuModel(CHECK_OUT, R.drawable.ic_home_checkout));
        menuModels.add(new MenuModel(HISTORY, R.drawable.ic_home_history));
        menuModels.add(new MenuModel(LEVEL, R.drawable.ic_home_level));
        menuModels.add(new MenuModel(DIVISION, R.drawable.ic_home_division));
        menuModels.add(new MenuModel(EMPLOYEE, R.drawable.ic_home_employee));
        return menuModels;
    }
}
