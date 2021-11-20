package com.app.absensis.network;

public class Route {
//    public static final String BASE_URL = "http://192.168.3.167:8081/api/";
    public static final String BASE_URL = "http://192.168.100.57:8081/api/";
    public static final String URL_LOGIN = "login";
    public static final String URL_PROFILE = "profile";
    public static final String URL_LOGOUT = "logout";

    // DIVISION
    public static final String URL_DIVISION_LIST = "division/get";
    public static final String URL_DIVISION_CREATE = "division/create";
    public static final String URL_DIVISION_UPDATE = "division/update";
    public static final String URL_DIVISION_DELETE = "division/delete";

    // LEVEL
    public static final String URL_LEVEL_LIST = "level/get";
    public static final String URL_LEVEL_CREATE = "level/create";
    public static final String URL_LEVEL_UPDATE = "level/update";
    public static final String URL_LEVEL_DELETE = "level/delete";

    // EMPLOYEE
    public static final String URL_EMPLOYEE_LIST = "employee/get";
    public static final String URL_EMPLOYEE_CREATE = "employee/create";
    public static final String URL_EMPLOYEE_UPDATE = "employee/update";
    public static final String URL_EMPLOYEE_DELETE = "employee/delete";

    // ATTENDANCE
    public static final String URL_REPORT_ATTENDANCE = "attendance/report";
    public static final String URL_CHECK_IN = "attendance/check-in";
    public static final String URL_CHECK_OUT = "attendance/check-out";
    public static final String URL_PERCENT = "attendance/percent";

}
