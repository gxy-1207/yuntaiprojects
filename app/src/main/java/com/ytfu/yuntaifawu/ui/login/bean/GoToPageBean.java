package com.ytfu.yuntaifawu.ui.login.bean;

public class GoToPageBean {

    /**
     * status : 200
     * msg : 已悬赏或者已解锁
     * type : 2
     * referer :
     * state : success
     */

    private int status;
    private String msg;
    private int type;
    private String referer;
    private String state;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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