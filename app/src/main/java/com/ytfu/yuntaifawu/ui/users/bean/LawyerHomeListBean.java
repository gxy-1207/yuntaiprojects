package com.ytfu.yuntaifawu.ui.users.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/11
*
*  @Des   首页律师列表和搜索列表
*
*/
public class LawyerHomeListBean {
    /**
     * code : 200
     * msg : 查询成功
     * data : [{"id":"38","lawyername":"马豹","expertplace":"婚姻家事,个人劳动争议,民间借贷,公司股权,买房纠纷","iconurl":"http://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png","mechanism":"北京秉理律师事务所","workyear":5}]
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
         * id : 38
         * lawyername : 马豹
         * expertplace : 婚姻家事,个人劳动争议,民间借贷,公司股权,买房纠纷
         * iconurl : http://yuntaifawu.com/data/upload/shenhe/2020-05-29/15907424317841.png
         * mechanism : 北京秉理律师事务所
         * workyear : 5
         */

        private String id;
        private String lawyername;
        private String expertplace;
        private String iconurl;
        private String mechanism;
        private int workyear;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLawyername() {
            return lawyername;
        }

        public void setLawyername(String lawyername) {
            this.lawyername = lawyername;
        }

        public String getExpertplace() {
            return expertplace;
        }

        public void setExpertplace(String expertplace) {
            this.expertplace = expertplace;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getMechanism() {
            return mechanism;
        }

        public void setMechanism(String mechanism) {
            this.mechanism = mechanism;
        }

        public int getWorkyear() {
            return workyear;
        }

        public void setWorkyear(int workyear) {
            this.workyear = workyear;
        }
    }
}
