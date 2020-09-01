package com.ytfu.yuntaifawu.ui.users.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/23
*
*  @Des 我的退款bean
*
*/
public class MyRefundBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"id":"1","userid":"3489","lsid":"2","content":"为什么要退款?","time":"2020-07-22 18:13:24","status":"3","voucher":"http://yuntaifawu.com/data/upload/voucher/3489_1595406542.jpg","order_id":"13864","reason":"3333333","price":"66.00"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userid : 3489
         * lsid : 2
         * content : 为什么要退款?
         * time : 2020-07-22 18:13:24
         * status : 3
         * voucher : http://yuntaifawu.com/data/upload/voucher/3489_1595406542.jpg
         * order_id : 13864
         * reason : 3333333
         * price : 66.00
         */

        private String id;
        private String userid;
        private String lsid;
        private String content;
        private String time;
        private String status;
        private String voucher;
        private String order_id;
        private String reason;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getLsid() {
            return lsid;
        }

        public void setLsid(String lsid) {
            this.lsid = lsid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}