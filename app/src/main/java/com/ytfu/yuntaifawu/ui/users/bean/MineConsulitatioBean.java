package com.ytfu.yuntaifawu.ui.users.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/12
*
*  @Des  我的咨询bean
*
*/
public  class MineConsulitatioBean {

    /**
     * list : [{"id":"93","uid":"4409","consult_type":"其他问题","consult_content":"我去美容院诱导的冲动消费了，我现在想退款，但是签了不予退款合同而且也录了音频了，请问除了协商还有机会要回来吗？","reward_price":"0.00","consult_date":"2020-06-12 01:52:48","sum":"0","unlock_type":"0","topping_date":"2020-06-12 01:52:48","con_type":"0","urgent":"0","status":"0","u_id":"4409","user_login":"哈莉","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/BqG5MVMQdHib9HEaL765NDH7n1cqL2y6WKtdsF2XvLbz0YJmyM86QhstUWfCicPxUAWvHbqRrH7D2c1uRsC0KPuQ/132","type":1},{"id":"91","uid":"2201","consult_type":"其他问题","consult_content":"是吗","reward_price":"0.00","consult_date":"2020-06-11 15:35:19","sum":"0","unlock_type":"0","topping_date":"2020-06-11 15:35:19","con_type":"0","urgent":"0","status":"1","u_id":"2201","user_login":"长亭外古道边芳草碧连天","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqYhFsxiaAFydqtwt6oz0pQ1B085WORYzcia5pdPAy7p9hes7gBs929ibI9z8V1pAibqtSK7JMiamCp22g/132","type":1}]
     * status : 1
     * referer :
     * state : success
     */

    private int status;
    private String referer;
    private String state;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 93
         * uid : 4409
         * consult_type : 其他问题
         * consult_content : 我去美容院诱导的冲动消费了，我现在想退款，但是签了不予退款合同而且也录了音频了，请问除了协商还有机会要回来吗？
         * reward_price : 0.00
         * consult_date : 2020-06-12 01:52:48
         * sum : 0
         * unlock_type : 0
         * topping_date : 2020-06-12 01:52:48
         * con_type : 0
         * urgent : 0
         * status : 0
         * u_id : 4409
         * user_login : 哈莉
         * avatar : https://thirdwx.qlogo.cn/mmopen/vi_32/BqG5MVMQdHib9HEaL765NDH7n1cqL2y6WKtdsF2XvLbz0YJmyM86QhstUWfCicPxUAWvHbqRrH7D2c1uRsC0KPuQ/132
         * type : 1
         */

        private String id;
        private String uid;
        private String consult_type;
        private String consult_content;
        private String reward_price;
        private String consult_date;
        private String sum;
        private String unlock_type;
        private String topping_date;
        private String con_type;
        private String urgent;
        private String status;
        private String u_id;
        private String user_login;
        private String avatar;
        private int type;
        private int price_type;

        public int getPrice_type() {
            return price_type;
        }

        public void setPrice_type(int price_type) {
            this.price_type = price_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getConsult_type() {
            return consult_type;
        }

        public void setConsult_type(String consult_type) {
            this.consult_type = consult_type;
        }

        public String getConsult_content() {
            return consult_content;
        }

        public void setConsult_content(String consult_content) {
            this.consult_content = consult_content;
        }

        public String getReward_price() {
            return reward_price;
        }

        public void setReward_price(String reward_price) {
            this.reward_price = reward_price;
        }

        public String getConsult_date() {
            return consult_date;
        }

        public void setConsult_date(String consult_date) {
            this.consult_date = consult_date;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getUnlock_type() {
            return unlock_type;
        }

        public void setUnlock_type(String unlock_type) {
            this.unlock_type = unlock_type;
        }

        public String getTopping_date() {
            return topping_date;
        }

        public void setTopping_date(String topping_date) {
            this.topping_date = topping_date;
        }

        public String getCon_type() {
            return con_type;
        }

        public void setCon_type(String con_type) {
            this.con_type = con_type;
        }

        public String getUrgent() {
            return urgent;
        }

        public void setUrgent(String urgent) {
            this.urgent = urgent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
