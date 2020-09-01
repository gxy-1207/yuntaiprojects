package com.ytfu.yuntaifawu.ui.lvshiwode.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/22
*
*  @Des   常用语分类bean
*
*/
public class ClassificationOfCommonWordsBean {

    /**
     * status : 1
     * list : [{"id":"1","content":"婚姻家事"},{"id":"2","content":"电商纠纷"},{"id":"3","content":"物业纠纷"},{"id":"4","content":"买房纠纷"},{"id":"5","content":"公司股权"},{"id":"6","content":"民间借贷"},{"id":"7","content":"交通事故"},{"id":"8","content":"个人劳动争议"},{"id":"9","content":"单位劳动争议"}]
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
         * id : 1
         * content : 婚姻家事
         */

        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}