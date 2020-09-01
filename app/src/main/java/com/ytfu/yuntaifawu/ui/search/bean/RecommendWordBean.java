package com.ytfu.yuntaifawu.ui.search.bean;

import java.util.List;

public class RecommendWordBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"id":"35","domain":"婚姻家事"},{"id":"44","domain":"电商纠纷"},{"id":"43","domain":"产品质量"},{"id":"42","domain":"物业纠纷"},{"id":"41","domain":"买房纠纷"},{"id":"40","domain":"公司股权"},{"id":"39","domain":"民间借贷"},{"id":"38","domain":"交通事故"},{"id":"37","domain":"单位劳动争议"},{"id":"36","domain":"个人劳动争议"},{"id":"45","domain":"刑事犯罪"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public List<DataBean> getData() { return data;}

    public void setData(List<DataBean> data) { this.data = data;}

    public static class DataBean {
        /**
         * id : 35
         * domain : 婚姻家事
         */

        private String id;
        private String domain;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public String getDomain() { return domain;}

        public void setDomain(String domain) { this.domain = domain;}
    }
}
