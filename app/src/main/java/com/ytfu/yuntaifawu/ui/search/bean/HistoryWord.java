package com.ytfu.yuntaifawu.ui.search.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class HistoryWord extends LitePalSupport {
    @Column(unique = true, defaultValue = "unknown")
    private String word;

    private long time;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
