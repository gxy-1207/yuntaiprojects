package com.ytfu.yuntaifawu.ui.login.bean;
/** @Auther gxy @Date 2020/7/8 @Des 微信登录绑定手机号 */
public class WechatBindingnumBean {

    private int status;
    private String uid;
    private String msg;
    private String shenfen;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getShenfen() {
        return shenfen;
    }

    public void setShenfen(String shenfen) {
        this.shenfen = shenfen;
    }
}
