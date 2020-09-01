package com.ytfu.yuntaifawu.ui.chat.bean;

import java.util.List;

public class UnLockMessage {

    /**
     * xiangqin : {"shuzu":["https://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png","https://yuntaifawu.com/data/upload/shenhe/2020-06-19/15925443362609.png"],"xiaoxi_arr":[{"avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png","user_login":"马豹律师","id":"233","uid":"832","content":"您好，您的问题我已有解决方案，如需进一步沟通，请与我私聊！","con_date":"2020-06-19 14:14:54","con_id":"240"},{"avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-06-19/15925443362609.png","user_login":"耿晨旭律师","id":"234","uid":"2","content":"您好，您的问题我有解决方案，","con_date":"2020-06-19 14:14:54","con_id":"240"}],"con_id":"240","count":"2","jiesuo_title":"获取所有律师解答方案","jiesuo_miaoshu":"20元","jiesuo_futitle":"所有律师解答均可查看","jiesuo_anniu":"点击解锁，获取咨询方案","jiesuo_chakan":"一次点击马上获取所有优质律师解答","jiesuo_price":"20.00"}
     * status : 200
     * con_type : 2
     * referer :
     * state : success
     */

    private XiangqinBean xiangqin;
    private int status;
    private int con_type;
    private String referer;
    private String state;

    public XiangqinBean getXiangqin() { return xiangqin;}

    public void setXiangqin(XiangqinBean xiangqin) { this.xiangqin = xiangqin;}

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public int getCon_type() { return con_type;}

    public void setCon_type(int con_type) { this.con_type = con_type;}

    public String getReferer() { return referer;}

    public void setReferer(String referer) { this.referer = referer;}

    public String getState() { return state;}

    public void setState(String state) { this.state = state;}

    public static class XiangqinBean {
        /**
         * shuzu : ["https://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png","https://yuntaifawu.com/data/upload/shenhe/2020-06-19/15925443362609.png"]
         * xiaoxi_arr : [{"avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png","user_login":"马豹律师","id":"233","uid":"832","content":"您好，您的问题我已有解决方案，如需进一步沟通，请与我私聊！","con_date":"2020-06-19 14:14:54","con_id":"240"},{"avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-06-19/15925443362609.png","user_login":"耿晨旭律师","id":"234","uid":"2","content":"您好，您的问题我有解决方案，","con_date":"2020-06-19 14:14:54","con_id":"240"}]
         * con_id : 240
         * count : 2
         * jiesuo_title : 获取所有律师解答方案
         * jiesuo_miaoshu : 20元
         * jiesuo_futitle : 所有律师解答均可查看
         * jiesuo_anniu : 点击解锁，获取咨询方案
         * jiesuo_chakan : 一次点击马上获取所有优质律师解答
         * jiesuo_price : 20.00
         */

        private String con_id;
        private String count;
        private String jiesuo_title;
        private String jiesuo_miaoshu;
        private String jiesuo_futitle;
        private String jiesuo_anniu;
        private String jiesuo_chakan;
        private double jiesuo_price;
        private List<String> shuzu;
        private List<XiaoxiArrBean> xiaoxi_arr;

        public String getCon_id() { return con_id;}

        public void setCon_id(String con_id) { this.con_id = con_id;}

        public String getCount() { return count;}

        public void setCount(String count) { this.count = count;}

        public String getJiesuo_title() { return jiesuo_title;}

        public void setJiesuo_title(String jiesuo_title) { this.jiesuo_title = jiesuo_title;}

        public String getJiesuo_miaoshu() { return jiesuo_miaoshu;}

        public void setJiesuo_miaoshu(String jiesuo_miaoshu) { this.jiesuo_miaoshu = jiesuo_miaoshu;}

        public String getJiesuo_futitle() { return jiesuo_futitle;}

        public void setJiesuo_futitle(String jiesuo_futitle) { this.jiesuo_futitle = jiesuo_futitle;}

        public String getJiesuo_anniu() { return jiesuo_anniu;}

        public void setJiesuo_anniu(String jiesuo_anniu) { this.jiesuo_anniu = jiesuo_anniu;}

        public String getJiesuo_chakan() { return jiesuo_chakan;}

        public void setJiesuo_chakan(String jiesuo_chakan) { this.jiesuo_chakan = jiesuo_chakan;}

        public double getJiesuo_price() { return jiesuo_price;}

        public void setJiesuo_price(double jiesuo_price) { this.jiesuo_price = jiesuo_price;}

        public List<String> getShuzu() { return shuzu;}

        public void setShuzu(List<String> shuzu) { this.shuzu = shuzu;}

        public List<XiaoxiArrBean> getXiaoxi_arr() { return xiaoxi_arr;}

        public void setXiaoxi_arr(List<XiaoxiArrBean> xiaoxi_arr) { this.xiaoxi_arr = xiaoxi_arr;}

        public static class XiaoxiArrBean {
            /**
             * avatar : https://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png
             * user_login : 马豹律师
             * id : 233
             * uid : 832
             * content : 您好，您的问题我已有解决方案，如需进一步沟通，请与我私聊！
             * con_date : 2020-06-19 14:14:54
             * con_id : 240
             */

            private String avatar;
            private String user_login;
            private String id;
            private String uid;
            private String content;
            private String con_date;
            private String con_id;

            public String getAvatar() { return avatar;}

            public void setAvatar(String avatar) { this.avatar = avatar;}

            public String getUser_login() { return user_login;}

            public void setUser_login(String user_login) { this.user_login = user_login;}

            public String getId() { return id;}

            public void setId(String id) { this.id = id;}

            public String getUid() { return uid;}

            public void setUid(String uid) { this.uid = uid;}

            public String getContent() { return content;}

            public void setContent(String content) { this.content = content;}

            public String getCon_date() { return con_date;}

            public void setCon_date(String con_date) { this.con_date = con_date;}

            public String getCon_id() { return con_id;}

            public void setCon_id(String con_id) { this.con_id = con_id;}
        }
    }
}
