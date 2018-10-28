package com.zj.quiz_community.async;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 16:07
 */
public enum  EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);

    private int value;

    public int getValue() {
        return value;
    }

    EventType(int value){
        this.value=value;
    }
}
