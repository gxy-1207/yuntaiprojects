package com.ytfu.yuntaifawu.ui.users.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/15
*
*  @Des  咨询详情bean
*
*/
public class ConsultationDetailsBean {

    /**
     * xiaoxi_arr : [{"avatar":"https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png","user_login":"名字","id":"63","uid":"2","content":"111","con_date":"2020-06-10 15:03:35","con_id":"77"}]
     * content : {"id":"77","uid":"1011","consult_type":"其他问题","consult_content":"我的房租老板不退给我\u2026\u2026","reward_price":"5.00","consult_date":"2020-06-15 16:11:38","sum":"0","unlock_type":"0","topping_date":"2020-06-15 16:11:38","con_type":"0","urgent":"5","status":"1","unlock_price":"0","avatar":"https://yuntaifawu.com/data/upload/avatar/2020-05-21//15900555461027.jpg","user_login":"走走D丢L","shuzu":["https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png"],"count":"1"}
     * msg : 成功
     * status : 200
     * referer :
     * state : success
     */

    private ContentBean content;
    private String msg;
    private int status;
    private String referer;
    private String state;
    private List<XiaoxiArrBean> xiaoxi_arr;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public List<XiaoxiArrBean> getXiaoxi_arr() {
        return xiaoxi_arr;
    }

    public void setXiaoxi_arr(List<XiaoxiArrBean> xiaoxi_arr) {
        this.xiaoxi_arr = xiaoxi_arr;
    }

    public static class ContentBean implements Parcelable {

        /**
         * id : 77
         * uid : 1011
         * consult_type : 其他问题
         * consult_content : 我的房租老板不退给我……
         * reward_price : 5.00
         * consult_date : 2020-06-15 16:11:38
         * sum : 0
         * unlock_type : 0
         * topping_date : 2020-06-15 16:11:38
         * con_type : 0
         * urgent : 5
         * status : 1
         * unlock_price : 0
         * avatar : https://yuntaifawu.com/data/upload/avatar/2020-05-21//15900555461027.jpg
         * user_login : 走走D丢L
         * shuzu : ["https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png"]
         * count : 1
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
        private String unlock_price;
        private String avatar;
        private String user_login;
        private String count;
        private int xuanshang_pay;
        private String jiesuo_title;
        private String jiesuo_miaoshu;
        private String jiesuo_futitle;
        private String jiesuo_fumiaoshu;
        private String jiesuo_price;
        private String jiesuo_anniu;
        private String jiesuo_chakan;
        private List<String> shuzu;

        private int is_first = 0;

        public int getIs_first() {
            return is_first;
        }

        public void setIs_first(int is_first) {
            this.is_first = is_first;
        }

        public static Creator<ContentBean> getCREATOR() {
            return CREATOR;
        }

        public String getJiesuo_anniu() {
            return jiesuo_anniu;
        }

        public void setJiesuo_anniu(String jiesuo_anniu) {
            this.jiesuo_anniu = jiesuo_anniu;
        }

        public String getJiesuo_chakan() {
            return jiesuo_chakan;
        }

        public void setJiesuo_chakan(String jiesuo_chakan) {
            this.jiesuo_chakan = jiesuo_chakan;
        }

        public String getJiesuo_title() {
            return jiesuo_title;
        }

        public void setJiesuo_title(String jiesuo_title) {
            this.jiesuo_title = jiesuo_title;
        }

        public String getJiesuo_miaoshu() {
            return jiesuo_miaoshu;
        }

        public void setJiesuo_miaoshu(String jiesuo_miaoshu) {
            this.jiesuo_miaoshu = jiesuo_miaoshu;
        }

        public String getJiesuo_futitle() {
            return jiesuo_futitle;
        }

        public void setJiesuo_futitle(String jiesuo_futitle) {
            this.jiesuo_futitle = jiesuo_futitle;
        }

        public String getJiesuo_fumiaoshu() {
            return jiesuo_fumiaoshu;
        }

        public void setJiesuo_fumiaoshu(String jiesuo_fumiaoshu) {
            this.jiesuo_fumiaoshu = jiesuo_fumiaoshu;
        }

        public String getJiesuo_price() {
            return jiesuo_price;
        }

        public void setJiesuo_price(String jiesuo_price) {
            this.jiesuo_price = jiesuo_price;
        }

        public int getXuanshang_pay() {
            return xuanshang_pay;
        }

        public void setXuanshang_pay(int xuanshang_pay) {
            this.xuanshang_pay = xuanshang_pay;
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

        public String getUnlock_price() {
            return unlock_price;
        }

        public void setUnlock_price(String unlock_price) {
            this.unlock_price = unlock_price;
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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<String> getShuzu() {
            return shuzu;
        }

        public void setShuzu(List<String> shuzu) {
            this.shuzu = shuzu;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.consult_type);
            dest.writeString(this.consult_content);
            dest.writeString(this.reward_price);
            dest.writeString(this.consult_date);
            dest.writeString(this.sum);
            dest.writeString(this.unlock_type);
            dest.writeString(this.topping_date);
            dest.writeString(this.con_type);
            dest.writeString(this.urgent);
            dest.writeString(this.status);
            dest.writeString(this.unlock_price);
            dest.writeString(this.avatar);
            dest.writeString(this.user_login);
            dest.writeString(this.count);
            dest.writeInt(this.xuanshang_pay);
            dest.writeString(this.jiesuo_title);
            dest.writeString(this.jiesuo_miaoshu);
            dest.writeString(this.jiesuo_futitle);
            dest.writeString(this.jiesuo_fumiaoshu);
            dest.writeString(this.jiesuo_price);
            dest.writeStringList(this.shuzu);
        }

        public ContentBean() {
        }

        protected ContentBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.consult_type = in.readString();
            this.consult_content = in.readString();
            this.reward_price = in.readString();
            this.consult_date = in.readString();
            this.sum = in.readString();
            this.unlock_type = in.readString();
            this.topping_date = in.readString();
            this.con_type = in.readString();
            this.urgent = in.readString();
            this.status = in.readString();
            this.unlock_price = in.readString();
            this.avatar = in.readString();
            this.user_login = in.readString();
            this.count = in.readString();
            this.xuanshang_pay = in.readInt();
            this.jiesuo_title = in.readString();
            this.jiesuo_miaoshu = in.readString();
            this.jiesuo_futitle = in.readString();
            this.jiesuo_fumiaoshu = in.readString();
            this.jiesuo_price = in.readString();
            this.shuzu = in.createStringArrayList();
        }

        public static final Parcelable.Creator<ContentBean> CREATOR = new Parcelable.Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel source) {
                return new ContentBean(source);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };
    }

    public static class XiaoxiArrBean {
        /**
         * avatar : https://yuntaifawu.com/data/upload/shenhe/2020-06-04/15912495868685.png
         * user_login : 名字
         * id : 63
         * uid : 2
         * content : 111
         * con_date : 2020-06-10 15:03:35
         * con_id : 77
         */

        private String avatar;
        private String user_login;
        private String id;
        private String uid;
        private String content;
        private String con_date;
        private String con_id;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            XiaoxiArrBean that = (XiaoxiArrBean) o;
            return Objects.equals(avatar, that.avatar) &&
                    Objects.equals(user_login, that.user_login) &&
                    Objects.equals(id, that.id) &&
                    Objects.equals(uid, that.uid) &&
                    Objects.equals(content, that.content) &&
                    Objects.equals(con_date, that.con_date) &&
                    Objects.equals(con_id, that.con_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(avatar, user_login, id, uid, content, con_date, con_id);
        }
    }
}
