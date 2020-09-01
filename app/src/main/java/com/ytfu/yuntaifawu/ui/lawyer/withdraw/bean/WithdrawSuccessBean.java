package com.ytfu.yuntaifawu.ui.lawyer.withdraw.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/7/9
*
*  @Des  提现成功bean
*
*/
public class WithdrawSuccessBean {

    /**
     * data : {"tixian":"1000","tixian_fuwu":100,"tixian_shiji":900}
     * status : 200
     * referer :
     * state : success
     */

    private DataBean data;
    private int status;
    private String referer;
    private String state;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * tixian : 1000
         * tixian_fuwu : 100
         * tixian_shiji : 900
         */

        private String tixian;
        private int tixian_fuwu;
        private int tixian_shiji;

        public String getTixian() {
            return tixian;
        }

        public void setTixian(String tixian) {
            this.tixian = tixian;
        }

        public int getTixian_fuwu() {
            return tixian_fuwu;
        }

        public void setTixian_fuwu(int tixian_fuwu) {
            this.tixian_fuwu = tixian_fuwu;
        }

        public int getTixian_shiji() {
            return tixian_shiji;
        }

        public void setTixian_shiji(int tixian_shiji) {
            this.tixian_shiji = tixian_shiji;
        }
    }
}