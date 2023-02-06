package com.example.database2try;

import java.util.Date;

public class LogIn {
    private int loginId;
    private Date loginDate;
    private int userId;

    public LogIn(){

    }

    public LogIn(Date loginDate, int userId) {
        this.loginDate = loginDate;
        this.userId = userId;
    }

    public LogIn(int loginId, Date loginDate, int userId) {
        this.loginId = loginId;
        this.loginDate = loginDate;
        this.userId = userId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LogIn{" +
                "loginId=" + loginId +
                ", loginDate=" + loginDate +
                ", userId=" + userId +
                '}';
    }
}