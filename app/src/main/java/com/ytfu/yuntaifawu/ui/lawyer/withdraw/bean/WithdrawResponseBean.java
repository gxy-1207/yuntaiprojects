package com.ytfu.yuntaifawu.ui.lawyer.withdraw.bean;

public class WithdrawResponseBean {
    /**
     * code : 202
     * msg : 最低提现100元，请重试
     * data : {"tixian_id":null}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tixian_id : null
         */

        private String tixian_id;

        public String getTixian_id() {
            return tixian_id;
        }

        public void setTixian_id(String tixian_id) {
            this.tixian_id = tixian_id;
        }
    }

//    /**
////     * code : 200
////     * msg : 提交成功
////     * data : []
////     */
////
////    private int code;
////    private String msg;
////    private List<?> data;
////
////    public int getCode() {
////        return code;
////    }
////
////    public void setCode(int code) {
////        this.code = code;
////    }
////
////    public String getMsg() {
////        return msg;
////    }
////
////    public void setMsg(String msg) {
////        this.msg = msg;
////    }
////
////    public List<?> getData() {
////        return data;
////    }
////
////    public void setData(List<?> data) {
////        this.data = data;
////    }

}
