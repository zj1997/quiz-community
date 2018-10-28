package com.zj.quiz_community.async.handler;

import com.zj.quiz_community.async.EventHandler;
import com.zj.quiz_community.async.EventModel;
import com.zj.quiz_community.async.EventType;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.pojo.Message;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.MessageService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\28 0028 - 16:22
 */
@Component
public class followerHandler implements EventHandler {


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void doHandler(EventModel eventModel) {

        Message message =  new Message();
        message.setCreatedDate(new Date());
        message.setFromId(MyUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwerId());

        User user = userService.selectById(eventModel.getActorId());

        if(eventModel.getEntityType() == EntityType.ENTITY_QUESTION){

            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" +eventModel.getEntityId());

        }else {

            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" +eventModel.getActorId());

        }

        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
