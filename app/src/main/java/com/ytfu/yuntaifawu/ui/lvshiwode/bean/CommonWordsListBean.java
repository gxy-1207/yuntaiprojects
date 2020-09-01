package com.ytfu.yuntaifawu.ui.lvshiwode.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/15
*
*  @Des  常用语列表
*
*/
public class CommonWordsListBean {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"id":"1","content":"hello,方便聊聊吗？"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * content : hello,方便聊聊吗？
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