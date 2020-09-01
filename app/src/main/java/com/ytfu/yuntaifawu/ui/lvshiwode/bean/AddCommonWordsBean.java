package com.ytfu.yuntaifawu.ui.lvshiwode.bean;

import java.util.List;

/**
*
*  @Auther  gxy
*
*  @Date    2020/7/15
*
*  @Des 添加常用语
*
*/
public class AddCommonWordsBean {

    /**
     * code : 200
     * msg : 添加成功
     * data : []
     */

    private int code;
    private String msg;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}