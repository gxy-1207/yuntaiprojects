package com.ytfu.yuntaifawu.ui.users.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/7/24
*
*  @Des  控制我的退款按钮显示隐藏
*
*/
public class RefundButtonVisibleBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : {"status":1}
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
         * status : 1
         */

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}