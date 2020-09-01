package com.ytfu.yuntaifawu.ui.complaint.bean;

import java.util.List;

public class ComplaintClassificationBean {

    /**
     * list : [{"label":"婚姻家事","id":"1","icon":"icon-ziyuan","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/hunyinjiashi@2x.png","href":"https://yuntaifawu.com/shunyin/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/hunyinjiashi.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"劳动争议","id":"2","icon":"icon-laodongrenshi","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/icon-ldrs@2x.png","href":"https://yuntaifawu.com/slaodong/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/laodongzhengyi.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"民间借贷","id":"4","icon":"icon-minjianjiedai","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/minjianjiedai-3@2x.png","href":"https://yuntaifawu.com/smingjian/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/minjianjiedai-2.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"交通事故","id":"6","icon":"icon-traffic-accident","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/jiaotongshigu-2@2x.png","href":"https://yuntaifawu.com/sjiaotong/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/jiaotongshigu-2.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"建设工程","id":"7","icon":"icon-jianshegongcheng","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/jianshegongcheng@2x.png","href":"https://yuntaifawu.com/sjianshe/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/jianshegongcheng.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"医疗纠纷","id":"8","icon":"icon-iconyiliaojiufen","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/iconyiliaojiufen@2x.png","href":"https://yuntaifawu.com/syiliao/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/yiliaojiufen.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"知识产权","id":"9","icon":"icon-zhishichanquan-copy","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/zhishichanquan@2x.png","href":"https://yuntaifawu.com/szhishi/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/zhishichanquan.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"},{"label":"房地产纠纷","id":"10","icon":"icon-fangdichan","img":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/fangdichan@2x.png","href":"https://yuntaifawu.com/sfangdichan/index","imgx":"http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/fangdichan.png","miaoshu":"婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事"}]
     * status : 1
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
         * label : 婚姻家事
         * id : 1
         * icon : icon-ziyuan
         * img : http://yuntaifawu.com//themes/simplebootx_mobile/Public/falv/icon/hunyinjiashi@2x.png
         * href : https://yuntaifawu.com/shunyin/index
         * imgx : http://yuntaifawu.com//themes/simplebootx_mobile/Public/newfenlei/hunyinjiashi.png
         * miaoshu : 婚姻家事婚姻家事婚姻家事婚姻家事婚姻家事
         */

        private String label;
        private String id;
        private String icon;
        private String img;
        private String href;
        private String imgx;
        private String miaoshu;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getImgx() {
            return imgx;
        }

        public void setImgx(String imgx) {
            this.imgx = imgx;
        }

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
        }
    }
}