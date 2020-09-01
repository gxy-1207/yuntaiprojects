package com.ytfu.yuntaifawu.domain;

public class EventBusMessage {

    public int arg1;
    public int arg2;
    public Object obj;

    public int what;

    @Override
    public String toString() {
        return "EventBusMessage{" +
                "arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", obj=" + obj +
                ", what=" + what +
                '}';
    }
}
