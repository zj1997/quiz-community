package com.zj.quiz_community.async;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 20:55
 */
public interface EventHandler {

     public void doHandler(EventModel eventModel);

     public List<EventType> getSupportEventType();

}
