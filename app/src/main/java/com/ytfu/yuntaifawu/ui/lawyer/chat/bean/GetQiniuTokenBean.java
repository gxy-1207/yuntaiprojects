package com.ytfu.yuntaifawu.ui.lawyer.chat.bean;

public class GetQiniuTokenBean {

    /**
     * token :
     * 4YIruATAqCo62LHqlWLPNKsKkOGbLC5erFUBmvtQ:ujLHoKbi90_5xt5VnvhTWTWsq7A=:eyJzY29wZSI6Inl1bnRhaS1jaGF0IiwiZGVhZGxpbmUiOjE2MDA2ODAxMzZ9
     * status : 1 referer : state : success
     */
    private String token;

    private int status;
    private String referer;
    private String state;

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
