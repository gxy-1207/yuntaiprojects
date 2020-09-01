package com.ytfu.yuntaifawu.ui.users.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/6/17
*
*  @Des  获取账户余额
*
*/
public class OverageBean {

    /**
     * status : 200
     * income : 982
     * referer :
     * state : success
     */

    private int status;
    private String income;
    private String referer;
    private String state;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
