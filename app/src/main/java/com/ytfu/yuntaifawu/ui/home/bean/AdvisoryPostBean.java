package com.ytfu.yuntaifawu.ui.home.bean;

/**发布咨询的数据相应*/
public class AdvisoryPostBean {

    /**
     * msg : 发布成功！
     * zixun_id : 102
     * status : 200
     * referer :
     * state : success
     */

    private String msg;
    private String zixun_id;
    private int status;
    private String referer;
    private String state;

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public String getZixun_id() { return zixun_id;}

    public void setZixun_id(String zixun_id) { this.zixun_id = zixun_id;}

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public String getReferer() { return referer;}

    public void setReferer(String referer) { this.referer = referer;}

    public String getState() { return state;}

    public void setState(String state) { this.state = state;}
}
