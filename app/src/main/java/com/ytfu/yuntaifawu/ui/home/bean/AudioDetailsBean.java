package com.ytfu.yuntaifawu.ui.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
/**
*
*  @Auther  gxy
*
*  @Date    2019/11/13
*
*  @Des  音频详情bean
*
*/
public class AudioDetailsBean {

    /**
     * status : 1
     * buy : 0
     * shoucang : 0
     * list : {"id":"243","order_count":"0","post_content":"<p style=\"white-space: normal; text-indent: 2em;\">上一讲当中说到了关于经济补偿金的问题，今天继续这块内容，首先是劳动合同终止有没有经济补偿的问题？<span style=\"text-indent: 2em;\">根据劳动合同法第四十六条第五款规定，除用人单位维持或者提高劳动合同约定条件续订劳动合同，劳动者不同意续订的情形外，依照本法第四十四条第一项规定终止固定期限劳动合同的，用人单位需要向劳动者支付经济补偿。<\/span><\/p><p><br/><\/p>","post_title":"劳动合同终止情形（三）","post_excerpt":"劳动合同终止有没有经济补偿？","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191010/5d9eed3fbd5b4.png","post_price":"19","post_cost":"99","post_audio":"https://www.yuntaifawu.com/data/upload/default/20191108/5dc5279eedf78.mp3","post_parent":"2392"}
     * audio_list : [{"post_excerpt":"如果单位要求员工有保密的义务，那么是否需要支付保密工资呢？对于保密工资，咱们国内法律法规以及很多地方政策法规都没有规定。","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1454","post_price":"39","post_cost":"99","post_title":"要求员工保密,单位要付保密工资吗?","id":"26"},{"post_excerpt":"保密协议核心条款以及关键要点主要有八个方面","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1210","post_price":"39","post_cost":"99","post_title":"保密协议的核心条款及关键要点有哪些?","id":"30"},{"post_excerpt":"病假和医疗期的区别，有哪些享受条件，长短因素以及申请病假，单位有没有绝对的审批权等","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1191","post_price":"39","post_cost":"99","post_title":"病假、医疗期的享受条件及风险防范风险","id":"36"},{"post_excerpt":"\u201c称职\u201d\u201c基本称职\u201d、\u201c不称职\u201d使用上的法律风险以及防范方法","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1386","post_price":"39","post_cost":"99","post_title":"\u201c不称职\u201d等同于不能胜任工作吗?","id":"37"},{"post_excerpt":"对于实行不定时工作制的员工能否主张加班费？很多人认为不需要，那么，实际上是否要支付加班费呢?","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1588","post_price":"39","post_cost":"99","post_title":"不定时工作制，能否主张加班费?","id":"38"},{"post_excerpt":"对于解除患病劳动者是有法律依据的，但需满足一定的条件且具体操作过程中需要几个注意点。","post_img":"https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png","order_count":"1285","post_price":"39","post_cost":"99","post_title":"能否解除患病员工，如何操作","id":"39"}]
     * contract_list : [{"id":"772","title":"劳动争议仲裁立案材料清单（劳动者申请仲裁）.docx","descript":"暂无描述","date":"2019-09-18 14:48:40","download_count":"1189","price":"59","realprice":"19","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0de21f142.png"},{"id":"773","title":"劳动争议仲裁立案材料清单（用人单位申请仲裁）.docx","descript":"暂无描述","date":"2019-09-18 14:48:40","download_count":"1679","price":"59","realprice":"29","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0de21f142.png"},{"id":"4481","title":"公司劳动争议调解委员会组织及工作条例","descript":"暂无描述","date":"2019-09-30 17:04:11","download_count":"1517","price":"59","realprice":"19","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0a546f63c.png"},{"id":"4495","title":"行政复议答辩状（劳动争议）","descript":"暂无描述","date":"2019-09-30 17:04:11","download_count":"1545","price":"59","realprice":"19","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0a546f63c.png"},{"id":"4496","title":"和解协议（劳动争议仲裁_诉讼中）","descript":"暂无描述","date":"2019-09-30 17:04:11","download_count":"1166","price":"59","realprice":"19","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0a546f63c.png"},{"id":"4995","title":"劳动争议案件代理词（格式模板）","descript":"暂无描述","date":"2019-10-09 17:06:47","download_count":"1000","price":"59","realprice":"19","img":"https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0a546f63c.png"}]
     * referer :
     * state : success
     */

    private int status;
    private int buy;
    private int shoucang;
    private ListBean list;
    private String referer;
    private String state;
    private List<AudioListBean> audio_list;
    private List<ContractListBean> contract_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getShoucang() {
        return shoucang;
    }

    public void setShoucang(int shoucang) {
        this.shoucang = shoucang;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
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

    public List<AudioListBean> getAudio_list() {
        return audio_list;
    }

    public void setAudio_list(List<AudioListBean> audio_list) {
        this.audio_list = audio_list;
    }

    public List<ContractListBean> getContract_list() {
        return contract_list;
    }

    public void setContract_list(List<ContractListBean> contract_list) {
        this.contract_list = contract_list;
    }

    public static class ListBean  {

        /**
         * id : 268
         * post_date : 2020-03-28 12:12:26
         * post_content : <p>声明：</p><p>&nbsp; &nbsp;本人专业从事离婚、劳动仲裁、借贷纠纷十余年，我只做这类案件，为您的案件提供从咨询、立案、开庭、到执行阶段的全套咨询方案。&nbsp;</p><p>&nbsp; &nbsp;本人只做我擅长的法律案件，对于其他类型案件不提供任何法律咨询，因为不熟悉，无法保证高质量的法律咨询服务。</p><p><br/></p>
         * post_yuanwen : <p style="text-align: center;"><strong><span style=";font-family:宋体;color:rgb(0,0,255);font-size:20px"><span style="font-family:宋体"></span></span></strong></p><p style="text-align:center"><span style="font-family: 宋体;font-size: 21px"><span style="font-family:宋体">养老保险没有交够十五年，我该怎么办？怎么补交？</span></span></p><p style="text-align: center;"><strong><span style=";font-family:宋体;color:rgb(0,0,255);font-size:20px"><span style="font-family:宋体"></span></span></strong><br/></p><p><br/></p><h1 style="margin-top:0;margin-right:0;margin-bottom:0;margin-left:0;text-indent:0;padding:0 0 0 0 "><strong><span style="font-family: 宋体;letter-spacing: 0;font-size: 22px;background: rgb(255, 255, 255)"><span style="font-family:宋体">大家好，我是马豹律师，专打劳动仲裁、离婚纠纷案件十余年，今天我教大家如何打劳动仲裁纠纷案件，对于</span></span></strong><strong><span style="font-family: 宋体;font-size: 20px"><span style="font-family:宋体">拖欠工资，单位不签劳动合同</span></span></strong><strong><span style="font-family: 宋体;letter-spacing: 0;font-size: 22px;background: rgb(255, 255, 255)"><span style="font-family:宋体">的法律问题进行详细法律分析，并出具解决方案，保护你的合法权益不受侵害</span></span></strong></h1><p><span style=";font-family:宋体;color:rgb(0,112,192);font-size:14px">&nbsp;</span></p><p><span style=";font-family:宋体;color:rgb(0,112,192);font-size:14px">&nbsp;</span></p><p style=";text-align:justify;text-justify:inter-ideograph"><span style=";font-family:宋体;color:rgb(0,112,192);font-size:29px"><span style="font-family:宋体">关于这个问题，咱们先从法律和事实方面分析一下：</span></span></p><p><span style=";font-family:宋体;font-size:14px">&nbsp;</span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">社会保险制度是我国的一项基本国策，养老保险作为社保制度中的核心组成部分，关乎到每一位参保人员的老年生活保障。近年来，国家建立了多层次的养老保险体系，只要交了养老保险，就有机会通过延交或者补交后领取退休金，只不过是如何领取和在哪里领的问题而已，领取退休金的基本条件是要累计交满养老保险</span>15年，对于达到退休年龄时缴费年限满10年不满15年的，经本人申请，当地社会保险经办机构批准，可办理缓退手续，至缴费年限满15年为止，缓退期间要按规定继续缴纳基本养老保险费。也可以转入新型农村社会养老保险或者城镇居民社会养老保险，按照国务院规定享受相应的养老保险待遇。</span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px">&nbsp;</span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px">&nbsp;</span></p><p><span style=";font-family:宋体;color:rgb(0,0,255);font-size:14px"><span style="font-family:宋体">法律依据：</span></span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">《社会保险法》第十条　职工应当参加基本养老保险，由用人单位和职工共同缴纳基本养老保险费。无雇工的个体工商户、未在用人单位参加基本养老保险的非全日制从业人员以及其他灵活就业人员可以参加基本养老保险，由个人缴纳基本养老保险费。公务员和参照公务员法管理的工作人员养老保险的办法由国务院规定。</span> 　</span></p><p style="text-indent:42px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">第十一条</span> <span style="font-family:宋体">基本养老保险实行社会统筹与个人账户相结合。基本养老保险基金由用人单位和个人缴费以及政府补贴等组成。</span> 　</span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">　第十二条　用人单位应当按照国家规定的本单位职工工资总额的比例缴纳基本养老保险费，记入基本养老保险统筹基金。职工应当按照国家规定的本人工资的比例缴纳基本养老保险费，记入个人账户。无雇工的个体工商户、未在用人单位参加基本养老保险的非全日制从业人员以及其他灵活就业人员参加基本养老保险的，应当按照国家规定缴纳基本养老保险费，分别记入基本养老保险统筹基金和个人账户。</span> </span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">第十五条　基本养老金由统筹养老金和个人账户养老金组成。基本养老金根据个人累计缴费年限、缴费工资、当地职工平均工资、个人账户金额、城镇人口平均预期寿命等因素确定。</span> 　</span></p><p style="text-indent:28px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">　第十六条　参加基本养老保险的个人，达到法定退休年龄时累计缴费满十五年的，按月领取基本养老金。</span></span></p><p><span style=";font-family:宋体;color:rgb(0,112,192);font-size:14px">&nbsp;</span></p><p style=";text-align:justify;text-justify:inter-ideograph"><span style=";font-family:宋体;color:rgb(0,112,192);font-size:29px"><span style="font-family:宋体">一般解决的程序</span></span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">养老保险到退休年龄没交满</span>15年，可以继续缴费到满15年，然后办理退休手续，按月领取退休金；一般是不可以一次性缴费后办理退休手续的。养老金能领取多少，主要看个人缴费年限长短、个人缴费基数高低和当地社会平均工资。 　　</span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">养老金</span>=基础养老金+个人账户养老金 　　</span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">个人账户养老金</span>=个人账户储存额÷计发月数(计发月数根据退休年龄和当时的人口平均寿命来确定。计发月数略等于(人口平均寿命-退休年龄)X12。目前50岁为195、55岁为170、60岁为139，不再统一是120了) 　　</span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">基础养老金</span> =(全省上年度在岗职工月平均工资+本人指数化月平均缴费工资)÷2×缴费年限×1% =全省上年度在岗职工月平均工资(1+本人平均缴费指数)÷2×缴费年限×1% 　 &nbsp;&nbsp;&nbsp;式中：本人指数化月平均缴费工资=全省上年度在岗职工月平均工资×本人平均缴费指数 　　在上述公式中可以看到，在缴费年限相同的情况下，基础养老金的高低取决于个人的平均缴费指数，个人的平均缴费指数就是自己实际的缴费基数与社会平均工资之比的历年平均值。低限为0.6，高限为3。</span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">因此，在养老金的两项计算中，无论何种情况，缴费基数越高，缴费的年限越长，养老金就会越高。养老金的领取是无限期规定的，只要领取人生存，就可以享受按月领取养老金的待遇，即使个人帐户养老金已经用完，仍然会继续按照原标准计发基础养老金，况且，个人养老金还要逐年根据社会在岗职工的月平均工资的增加而增长。因此，活得越久，就可以领取得越多，相对于交费来说，肯定更加划算。</span></span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style=";font-family:宋体;font-size:14px"><span style="font-family:宋体">有的律师为了承接案件，会告诉你案件特别复杂，很难打赢，其实只是知道这几点，谁都可以打赢这个官司，法官也是根据这些法律条文来判案，全国法律都一样，没有区别，所以律师不会告诉您具体的诉讼方案，我们在这边会毫无保留的告诉大家怎么样打官司，怎么样准备证据，怎么样罗列证据清单，准备开庭意见。</span></span><span style=";font-family:宋体;font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p style="text-indent:28px;text-autospace:ideograph-numeric;line-height:29px"><span style="font-family: Calibri;font-size: 14px">&nbsp;</span></p><p><span style=";font-family:宋体;font-size:14px">&nbsp;</span></p><p><span style=";font-family:宋体;font-size:14px">&nbsp;</span></p><p><br/></p>
         * post_title : 养老保险没有交够十五年，我该怎么办？怎么补交？
         * post_excerpt :
         * post_status : 1
         * post_parent : 2528
         * post_stage : 0
         * post_img : https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ece986a6a5.jpg
         * big_img : admin/20200328/5e7ece986a6a5.jpg
         * post_audio : https://yuntaifawu.com/data/upload/default/20200407/5e8c2e9fb0e9b.mp3
         * post_url : https://yuntaifawu.com/data/upload/default/20200328/5e7ece06c48f3.doc
         * order_count : 546848
         * post_cost : 99
         * post_price : 19
         * post_zhushou_si : 277
         * post_zhushou : 0
         * post_zhushou_er : 273
         * post_zhushou_san : 274
         * tuijian : 0
         * lvshi_id : 44
         * lvshi_descript :  资深劳动纠纷律师
         * lvshi_name : 马律师
         * lvshi_img : https://www.yuntaifawu.com/data/upload/admin/20200328/5e7ec0c64761c.png
         * play_time : 0
         * time : 05:29
         * miao : 329
         */

        private String id;
        private String post_date;
        private String post_content;
        private String post_yuanwen;
        private String post_title;
        private String post_excerpt;
        private String post_status;
        private String post_parent;
        private String post_stage;
        private String post_img;
        private String big_img;
        private String post_audio;
        private String post_url;
        private String order_count;
        private String post_cost;
        private String post_price;
        private String post_zhushou_si;
        private String post_zhushou;
        private String post_zhushou_er;
        private String post_zhushou_san;
        private String tuijian;
        private String lvshi_id;
        private String lvshi_descript;
        private String lvshi_name;
        private String lvshi_img;
        private int play_time;
        private String time;
        private int miao;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public String getPost_date() { return post_date;}

        public void setPost_date(String post_date) { this.post_date = post_date;}

        public String getPost_content() { return post_content;}

        public void setPost_content(String post_content) { this.post_content = post_content;}

        public String getPost_yuanwen() { return post_yuanwen;}

        public void setPost_yuanwen(String post_yuanwen) { this.post_yuanwen = post_yuanwen;}

        public String getPost_title() { return post_title;}

        public void setPost_title(String post_title) { this.post_title = post_title;}

        public String getPost_excerpt() { return post_excerpt;}

        public void setPost_excerpt(String post_excerpt) { this.post_excerpt = post_excerpt;}

        public String getPost_status() { return post_status;}

        public void setPost_status(String post_status) { this.post_status = post_status;}

        public String getPost_parent() { return post_parent;}

        public void setPost_parent(String post_parent) { this.post_parent = post_parent;}

        public String getPost_stage() { return post_stage;}

        public void setPost_stage(String post_stage) { this.post_stage = post_stage;}

        public String getPost_img() { return post_img;}

        public void setPost_img(String post_img) { this.post_img = post_img;}

        public String getBig_img() { return big_img;}

        public void setBig_img(String big_img) { this.big_img = big_img;}

        public String getPost_audio() { return post_audio;}

        public void setPost_audio(String post_audio) { this.post_audio = post_audio;}

        public String getPost_url() { return post_url;}

        public void setPost_url(String post_url) { this.post_url = post_url;}

        public String getOrder_count() { return order_count;}

        public void setOrder_count(String order_count) { this.order_count = order_count;}

        public String getPost_cost() { return post_cost;}

        public void setPost_cost(String post_cost) { this.post_cost = post_cost;}

        public String getPost_price() { return post_price;}

        public void setPost_price(String post_price) { this.post_price = post_price;}

        public String getPost_zhushou_si() { return post_zhushou_si;}

        public void setPost_zhushou_si(String post_zhushou_si) { this.post_zhushou_si = post_zhushou_si;}

        public String getPost_zhushou() { return post_zhushou;}

        public void setPost_zhushou(String post_zhushou) { this.post_zhushou = post_zhushou;}

        public String getPost_zhushou_er() { return post_zhushou_er;}

        public void setPost_zhushou_er(String post_zhushou_er) { this.post_zhushou_er = post_zhushou_er;}

        public String getPost_zhushou_san() { return post_zhushou_san;}

        public void setPost_zhushou_san(String post_zhushou_san) { this.post_zhushou_san = post_zhushou_san;}

        public String getTuijian() { return tuijian;}

        public void setTuijian(String tuijian) { this.tuijian = tuijian;}

        public String getLvshi_id() { return lvshi_id;}

        public void setLvshi_id(String lvshi_id) { this.lvshi_id = lvshi_id;}

        public String getLvshi_descript() { return lvshi_descript;}

        public void setLvshi_descript(String lvshi_descript) { this.lvshi_descript = lvshi_descript;}

        public String getLvshi_name() { return lvshi_name;}

        public void setLvshi_name(String lvshi_name) { this.lvshi_name = lvshi_name;}

        public String getLvshi_img() { return lvshi_img;}

        public void setLvshi_img(String lvshi_img) { this.lvshi_img = lvshi_img;}

        public int getPlay_time() { return play_time;}

        public void setPlay_time(int play_time) { this.play_time = play_time;}

        public String getTime() { return time;}

        public void setTime(String time) { this.time = time;}

        public int getMiao() { return miao;}

        public void setMiao(int miao) { this.miao = miao;}
    }

    public static class AudioListBean implements Parcelable {

        /**
         * post_excerpt : 如果单位要求员工有保密的义务，那么是否需要支付保密工资呢？对于保密工资，咱们国内法律法规以及很多地方政策法规都没有规定。
         * post_img : https://www.yuntaifawu.com/data/upload/admin/20191018/5da9884b1a659.png
         * order_count : 1454
         * post_price : 39
         * post_cost : 99
         * post_title : 要求员工保密,单位要付保密工资吗?
         * id : 26
         */

        private String post_excerpt;
        private String post_img;
        private String order_count;
        private String post_price;
        private String post_cost;
        private String post_title;
        private String id;

        public String getPost_excerpt() {
            return post_excerpt;
        }

        public void setPost_excerpt(String post_excerpt) {
            this.post_excerpt = post_excerpt;
        }

        public String getPost_img() {
            return post_img;
        }

        public void setPost_img(String post_img) {
            this.post_img = post_img;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getPost_price() {
            return post_price;
        }

        public void setPost_price(String post_price) {
            this.post_price = post_price;
        }

        public String getPost_cost() {
            return post_cost;
        }

        public void setPost_cost(String post_cost) {
            this.post_cost = post_cost;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.post_excerpt);
            dest.writeString(this.post_img);
            dest.writeString(this.order_count);
            dest.writeString(this.post_price);
            dest.writeString(this.post_cost);
            dest.writeString(this.post_title);
            dest.writeString(this.id);
        }

        public AudioListBean() {}

        protected AudioListBean(Parcel in) {
            this.post_excerpt = in.readString();
            this.post_img = in.readString();
            this.order_count = in.readString();
            this.post_price = in.readString();
            this.post_cost = in.readString();
            this.post_title = in.readString();
            this.id = in.readString();
        }

        public static final Parcelable.Creator<AudioListBean> CREATOR = new Parcelable.Creator<AudioListBean>() {
            @Override
            public AudioListBean createFromParcel(Parcel source) {return new AudioListBean(source);}

            @Override
            public AudioListBean[] newArray(int size) {return new AudioListBean[size];}
        };
    }

    public static class ContractListBean {
        /**
         * id : 772
         * title : 劳动争议仲裁立案材料清单（劳动者申请仲裁）.docx
         * descript : 暂无描述
         * date : 2019-09-18 14:48:40
         * download_count : 1189
         * price : 59
         * realprice : 19
         * img : https://www.yuntaifawu.com/data/upload/admin/20191021/5dad0de21f142.png
         */

        private String id;
        private String title;
        private String descript;
        private String date;
        private String download_count;
        private String price;
        private String realprice;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDownload_count() {
            return download_count;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRealprice() {
            return realprice;
        }

        public void setRealprice(String realprice) {
            this.realprice = realprice;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
