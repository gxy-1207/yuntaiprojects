package com.ytfu.yuntaifawu.ui.lvshihetong.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/6/29
*
*  @Des  现在预览次数bean
*
*/
public class DownloadPreviewBean {

    /**
     * status : 1
     * referer :
     * state : success
     */

    private int status;
    private String referer;
    private String state;

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
