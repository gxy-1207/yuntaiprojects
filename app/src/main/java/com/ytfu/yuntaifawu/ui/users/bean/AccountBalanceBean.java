package com.ytfu.yuntaifawu.ui.users.bean;

public class AccountBalanceBean {

    /**
     * status : 200
     * income : 0
     * referer :
     * state : success
     */

    private int status;
    private double income;
    private String referer;
    private String state;

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public double getIncome() { return income;}

    public void setIncome(double income) { this.income = income;}

    public String getReferer() { return referer;}

    public void setReferer(String referer) { this.referer = referer;}

    public String getState() { return state;}

    public void setState(String state) { this.state = state;}
}
