package com.ytfu.yuntaifawu.ui.lawyer.chat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

@Keep
public class HistoryChatExtBean implements Parcelable {
    @Override
    public String toString() {
        return "HistoryChatExtBean{" +
                "jiluid='" + jiluid + '\'' +
                ", lvshiid='" + lvshiid + '\'' +
                ", userid='" + userid + '\'' +
                ", price='" + price + '\'' +
                ", type=" + type +
                ", rpcontent='" + rpcontent + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    /**
     *  "huashuid":"15910655635",    }
     *
     *
     * jiluid : 15906521994
     * lvshiid : 3
     * userid : 4286
     * price : 0.0001
     * type : 1
     */

    private String jiluid;
    private String lvshiid;
    private String userid;
    private String price;
    private int type;// 1 未支付 2 已支付  3红包
    private String rpcontent;
    private String iconurl;
    private String nickname;


    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRpcontent() {
        return rpcontent;
    }

    public void setRpcontent(String rpcontent) {
        this.rpcontent = rpcontent;
    }

    public String getJiluid() {
        return jiluid;
    }

    public void setJiluid(String jiluid) {
        this.jiluid = jiluid;
    }

    public String getLvshiid() {
        return lvshiid;
    }

    public void setLvshiid(String lvshiid) {
        this.lvshiid = lvshiid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jiluid);
        dest.writeString(this.lvshiid);
        dest.writeString(this.userid);
        dest.writeString(this.price);
        dest.writeInt(this.type);
        dest.writeString(this.rpcontent);
        dest.writeString(this.iconurl);
        dest.writeString(this.nickname);
    }

    public HistoryChatExtBean() {}

    protected HistoryChatExtBean(Parcel in) {
        this.jiluid = in.readString();
        this.lvshiid = in.readString();
        this.userid = in.readString();
        this.price = in.readString();
        this.type = in.readInt();
        this.rpcontent = in.readString();
        this.iconurl = in.readString();
        this.nickname = in.readString();
    }

    public static final Parcelable.Creator<HistoryChatExtBean> CREATOR = new Parcelable.Creator<HistoryChatExtBean>() {
        @Override
        public HistoryChatExtBean createFromParcel(Parcel source) {return new HistoryChatExtBean(source);}

        @Override
        public HistoryChatExtBean[] newArray(int size) {return new HistoryChatExtBean[size];}
    };
}
