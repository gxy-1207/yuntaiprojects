package com.ytfu.yuntaifawu.ui.users.bean;

import java.util.List;

/** @Auther gxy @Date 2020/6/12 @Des 公告bean */
public class AnnouncementBean {

  /**
   * code : 200 msg : 查询成功 data : [{"id":"7","title":"周末放假啊啊啊","addtime":"2020-06-12
   * 13:25:00","istop":"1","jumpurl":"http://yuntaifawu.com/Notice/Notice?id=7"}]
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
     * id : 7 title : 周末放假啊啊啊 addtime : 2020-06-12 13:25:00 istop : 1 jumpurl :
     * http://yuntaifawu.com/Notice/Notice?id=7
     */
    private String id;

    private String title;
    private String addtime;
    private String istop;
    private String jumpurl;
    private String content;
    private int rand_type;

    public int getRand_type() {
      return rand_type;
    }

    public void setRand_type(int rand_type) {
      this.rand_type = rand_type;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

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

    public String getAddtime() {
      return addtime;
    }

    public void setAddtime(String addtime) {
      this.addtime = addtime;
    }

    public String getIstop() {
      return istop;
    }

    public void setIstop(String istop) {
      this.istop = istop;
    }

    public String getJumpurl() {
      return jumpurl;
    }

    public void setJumpurl(String jumpurl) {
      this.jumpurl = jumpurl;
    }
  }
}
