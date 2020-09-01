package com.ytfu.yuntaifawu.ui.home.bean;

import java.util.List;

public class RandomLawyerResponse {
    /**
     * code : 200
     * msg : 查询成功
     * data : [{"iconurl":"https://yuntaifawu.com/data/upload/shenhe/2020-07-08/15942015473160.png","lsid":"10644"}]
     */

    private int code;
    private String msg;
    private D data;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public static class D{
        private List<DataBean> result;
        private int num;

        public List<DataBean> getResult() {
            return result;
        }

        public void setResult(List<DataBean> result) {
            this.result = result;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class DataBean {
        /**
         * iconurl : https://yuntaifawu.com/data/upload/shenhe/2020-07-08/15942015473160.png
         * lsid : 10644
         */

        private String iconurl;
        private String lsid;

        public String getIconurl() { return iconurl;}

        public void setIconurl(String iconurl) { this.iconurl = iconurl;}

        public String getLsid() { return lsid;}

        public void setLsid(String lsid) { this.lsid = lsid;}
    }
}
