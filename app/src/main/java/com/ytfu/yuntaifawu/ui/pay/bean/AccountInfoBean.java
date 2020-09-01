package com.ytfu.yuntaifawu.ui.pay.bean;

public class AccountInfoBean {

    /**
     * status : 200
     * jiaji : 10
     * zhuijia_title : 追加10元平台加急费
     * zhuijia_descript : 悬赏加急，享受平台优先推送，推荐优选律师为您解答
     * income : 0
     * referer :
     * state : success
     */

    private int status;
    private double jiaji;
    private String zhuijia_title;
    private String zhuijia_descript;
    private double income;
    private String referer;
    private String state;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getJiaji() {
        return jiaji;
    }

    public void setJiaji(double jiaji) {
        this.jiaji = jiaji;
    }

    public String getZhuijia_title() {
        return zhuijia_title;
    }

    public void setZhuijia_title(String zhuijia_title) {
        this.zhuijia_title = zhuijia_title;
    }

    public String getZhuijia_descript() {
        return zhuijia_descript;
    }

    public void setZhuijia_descript(String zhuijia_descript) {
        this.zhuijia_descript = zhuijia_descript;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
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
