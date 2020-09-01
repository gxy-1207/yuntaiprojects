package com.ytfu.yuntaifawu.ui.zixun.bean;
/**
*
*  @Auther  gxy
*
*  @Date    2020/6/16
*
*  @Des  评价详情bean
*
*/
public class ReplyDetailBean {

    /**
     * status : 200
     * find : {"id":"63","uid":"2","content":"111","con_date":"2020-06-10 15:03:35","con_id":"77","avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png","user_login":"郭先生律师"}
     * referer :
     * state : success
     */

    private int status;
    private FindBean find;
    private String referer;
    private String state;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FindBean getFind() {
        return find;
    }

    public void setFind(FindBean find) {
        this.find = find;
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

    public static class FindBean {
        /**
         * id : 63
         * uid : 2
         * content : 111
         * con_date : 2020-06-10 15:03:35
         * con_id : 77
         * avatar : https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png
         * user_login : 郭先生律师
         */

        private String id;
        private String uid;
        private String content;
        private String con_date;
        private String con_id;
        private String avatar;
        private String user_login;
        private int con_type;

        public int getCon_type() {
            return con_type;
        }

        public void setCon_type(int con_type) {
            this.con_type = con_type;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCon_date() {
            return con_date;
        }

        public void setCon_date(String con_date) {
            this.con_date = con_date;
        }

        public String getCon_id() {
            return con_id;
        }

        public void setCon_id(String con_id) {
            this.con_id = con_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }
    }
}
