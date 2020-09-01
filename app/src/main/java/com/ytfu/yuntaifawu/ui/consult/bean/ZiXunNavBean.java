package com.ytfu.yuntaifawu.ui.consult.bean;

import java.util.List;

public class ZiXunNavBean {

    /**
     * list : [{"name":"婚姻家事"},{"name":"个人劳动争议"},{"name":"单位劳动争议"},{"name":"交通事故"},{"name":"民间借贷"},{"name":"公司股权"},{"name":"买房纠纷"},{"name":"物业纠纷"},{"name":"产品质量"},{"name":"电商纠纷"},{"name":"刑事犯罪"},{"name":"其他问题"}]
     * status : 1
     * lvshi : 156
     * sum : 500
     * referer :
     * state : success
     */

    private int status;
    private int lvshi;
    private int sum;
    private String referer;
    private String state;
    private List<ListBean> list;
    private String zixun_descript;
    private String xuanshang_descript;
    private String new_zixun_xiangqing;

    public String getNew_zixun_xiangqing() {
        return new_zixun_xiangqing;
    }

    public void setNew_zixun_xiangqing(String new_zixun_xiangqing) {
        this.new_zixun_xiangqing = new_zixun_xiangqing;
    }

    public String getZixun_descript() {
        return zixun_descript;
    }

    public void setZixun_descript(String zixun_descript) {
        this.zixun_descript = zixun_descript;
    }

    public String getXuanshang_descript() {
        return xuanshang_descript;
    }

    public void setXuanshang_descript(String xuanshang_descript) {
        this.xuanshang_descript = xuanshang_descript;
    }
    //     "zixun_descript": "请详细的描述您的法律需求，以便得到律师的专业意见，请注意个人隐私，不要出现双方的真实姓名～",
//             "xuanshang_descript": "悬赏咨询，律师在线解答不用等，",

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLvshi() {
        return lvshi;
    }

    public void setLvshi(int lvshi) {
        this.lvshi = lvshi;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
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
         * name : 婚姻家事
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
