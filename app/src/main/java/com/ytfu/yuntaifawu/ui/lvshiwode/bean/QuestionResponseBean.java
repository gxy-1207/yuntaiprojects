package com.ytfu.yuntaifawu.ui.lvshiwode.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionResponseBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : {"status":"1","helpurl":"http://yuntaifawu.com/chat/questionhelp","content":"您好，您的问题我已有解决方案，如需进一步沟通，请与我私聊！","domain":"婚姻家事, \t\t个人劳动争议, \t\t单位劳动争议, \t\t交通事故, \t\t民间借贷, \t\t公司股权, \t\t买房纠纷, \t\t物业纠纷, \t\t产品质量, \t\t电商纠纷, \t\t刑事犯罪"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public static class DataBean implements Parcelable {


        /**
         * status : 1
         * helpurl : http://yuntaifawu.com/chat/questionhelp
         * content : 您好，您的问题我已有解决方案，如需进一步沟通，请与我私聊！
         * domain : 婚姻家事, 		个人劳动争议, 		单位劳动争议, 		交通事故, 		民间借贷, 		公司股权, 		买房纠纷, 		物业纠纷, 		产品质量, 		电商纠纷, 		刑事犯罪
         */

        private int status;
        private String helpurl;
        private String content;
        private String domain;

        public int getStatus() { return status;}

        public void setStatus(int status) { this.status = status;}

        public String getHelpurl() { return helpurl;}

        public void setHelpurl(String helpurl) { this.helpurl = helpurl;}

        public String getContent() { return content;}

        public void setContent(String content) { this.content = content;}

        public String getDomain() { return domain;}

        public void setDomain(String domain) { this.domain = domain;}


        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeString(this.helpurl);
            dest.writeString(this.content);
            dest.writeString(this.domain);
        }

        public DataBean() {}

        protected DataBean(Parcel in) {
            this.status = in.readInt();
            this.helpurl = in.readString();
            this.content = in.readString();
            this.domain = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {return new DataBean(source);}

            @Override
            public DataBean[] newArray(int size) {return new DataBean[size];}
        };
    }
}
