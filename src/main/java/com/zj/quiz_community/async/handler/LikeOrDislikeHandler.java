package com.zj.quiz_community.async.handler;

import com.zj.quiz_community.async.EventHandler;
import com.zj.quiz_community.async.EventModel;
import com.zj.quiz_community.async.EventType;
import com.zj.quiz_community.pojo.Message;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.MessageService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 21:33
 */
@Component
public class LikeOrDislikeHandler implements EventHandler{

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel model) {

        Message message = new Message();

        message.setToId(model.getEntityOwerId());
        message.setFromId(MyUtil.SYSTEM_USERID);
        message.setCreatedDate(new Date());
        User user = userService.selectById(model.getActorId());

        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));

        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }

}
