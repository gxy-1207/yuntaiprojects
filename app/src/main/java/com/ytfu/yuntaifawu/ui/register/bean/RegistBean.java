package com.ytfu.yuntaifawu.ui.register.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2019/11/15
*
*  @Des  注册bean
*
*/
public class RegistBean {

    /**
     * uid :
     * msg :
     * status : 2
     * referer :
     * state : success
     */

    private String uid;
    private String msg;
    private int status;
    private String referer;
    private String state;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
