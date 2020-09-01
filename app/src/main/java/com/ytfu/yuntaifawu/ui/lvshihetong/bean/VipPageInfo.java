package com.ytfu.yuntaifawu.ui.lvshihetong.bean;

import java.util.List;

public class VipPageInfo {

    /**
     * contract : [{"id":"2226","title":"公司值班室管理规定","download_count":"1998"},{"id":"3617","title":"茶叶买卖合同（安徽省2014版）","download_count":"1996"},{"id":"4716","title":"保密协议（参与考试工作）","download_count":"1994"},{"id":"1522","title":"签约催告函（中介公司给客户）","download_count":"1980"},{"id":"5626","title":"大学生志愿服务西部计划合同","download_count":"1968"}]
     * audio : [{"id":"268","post_title":"养老保险没有交够十五年，我该怎么办？怎么补交？","post_img":"https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ece8a071f0.jpg","order_count":"546848","label":"个人劳动争议咨询"},{"id":"267","post_title":"公司给我解除了劳动合同，但不给我出解除劳动合同证明、离职证明，我该怎么办？","post_img":"https://www.yuntaifawu.com/data/upload/admin/20200328/5e7eccd3b9cbc.jpg","order_count":"98983","label":"个人劳动争议咨询"},{"id":"273","post_title":"我们单位因为效益不好，企业转行，想要裁员，让我自己离职，我们应该怎么样和单位谈判，获得赔偿？","post_img":"https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ed2041ce6e.jpg","order_count":"54156","label":"个人劳动争议咨询"},{"id":"275","post_title":"我在异地买保险，能转回我老家吗？怎么样合并计算？","post_img":"https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ed2d2f1fa7.jpg","order_count":"15546","label":"个人劳动争议咨询"},{"id":"149","post_title":"如何公示才更符合举证要求","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191227/5e05b84f18eaa.jpg","order_count":"11195","label":"单位劳动争议咨询"},{"id":"349","post_title":"电动车性质认定及搭便车责任","post_img":"https://www.yuntaifawu.com/data/upload/admin/20200330/5e81c046ce38c.jpg","order_count":"9982","label":"交通事故咨询"}]
     * status : 1
     * referer :
     * state : success
     */

    private int status;
    private String referer;
    private String state;
    private List<ContractBean> contract;
    private List<AudioBean> audio;

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public String getReferer() { return referer;}

    public void setReferer(String referer) { this.referer = referer;}

    public String getState() { return state;}

    public void setState(String state) { this.state = state;}

    public List<ContractBean> getContract() { return contract;}

    public void setContract(List<ContractBean> contract) { this.contract = contract;}

    public List<AudioBean> getAudio() { return audio;}

    public void setAudio(List<AudioBean> audio) { this.audio = audio;}

    public static class ContractBean {
        /**
         * id : 2226
         * title : 公司值班室管理规定
         * download_count : 1998
         */

        private String id;
        private String title;
        private String download_count;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public String getTitle() { return title;}

        public void setTitle(String title) { this.title = title;}

        public String getDownload_count() { return download_count;}

        public void setDownload_count(String download_count) { this.download_count = download_count;}
    }

    public static class AudioBean {
        /**
         * id : 268
         * post_title : 养老保险没有交够十五年，我该怎么办？怎么补交？
         * post_img : https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ece8a071f0.jpg
         * order_count : 546848
         * label : 个人劳动争议咨询
         */

        private String id;
        private String post_title;
        private String post_img;
        private String order_count;
        private String label;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public String getPost_title() { return post_title;}

        public void setPost_title(String post_title) { this.post_title = post_title;}

        public String getPost_img() { return post_img;}

        public void setPost_img(String post_img) { this.post_img = post_img;}

        public String getOrder_count() { return order_count;}

        public void setOrder_count(String order_count) { this.order_count = order_count;}

        public String getLabel() { return label;}

        public void setLabel(String label) { this.label = label;}
    }
}
