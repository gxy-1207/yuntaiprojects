package com.ytfu.yuntaifawu.ui.users.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/6/17
*
*  @Des  公共价格的接口bean
 *  悬赏金额数组
*
*/
public class PublicPriceBean {

    /**
     * jiaji : 10
     * jiesuo : 20
     * caina : 5
     * top : 5
     * xuanshang_descript : 悬赏咨询，律师在线解答不用等，
     * zixun_descript : 请详细的描述您的法律需求，以便得到律师的专业意见，请注意个人隐私，不要出现双方的真实姓名～
     * zhuijia_title : 追加10元平台加急费
     * zhuijia_descript : 悬赏加急，享受平台优先推送，推荐优选律师为您解答
     * jiesuo_title : 点击解锁，获取咨询方案
     * jiesuo_descript : 一次点击马上获取所有优质律师解答
     * status : 1
     * referer :
     * state : success
     */

    private String jiaji;
    private String jiesuo;
    private String caina;
    private String top;
    private String xuanshang_descript;
    private String zixun_descript;
    private String zhuijia_title;
    private String zhuijia_descript;
    private String jiesuo_title;
    private String jiesuo_descript;
    private int status;
    private String referer;
    private String state;

    public String getJiaji() {
        return jiaji;
    }

    public void setJiaji(String jiaji) {
        this.jiaji = jiaji;
    }

    public String getJiesuo() {
        return jiesuo;
    }

    public void setJiesuo(String jiesuo) {
        this.jiesuo = jiesuo;
    }

    public String getCaina() {
        return caina;
    }

    public void setCaina(String caina) {
        this.caina = caina;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getXuanshang_descript() {
        return xuanshang_descript;
    }

    public void setXuanshang_descript(String xuanshang_descript) {
        this.xuanshang_descript = xuanshang_descript;
    }

    public String getZixun_descript() {
        return zixun_descript;
    }

    public void setZixun_descript(String zixun_descript) {
        this.zixun_descript = zixun_descript;
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

    public String getJiesuo_title() {
        return jiesuo_title;
    }

    public void setJiesuo_title(String jiesuo_title) {
        this.jiesuo_title = jiesuo_title;
    }

    public String getJiesuo_descript() {
        return jiesuo_descript;
    }

    public void setJiesuo_descript(String jiesuo_descript) {
        this.jiesuo_descript = jiesuo_descript;
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
