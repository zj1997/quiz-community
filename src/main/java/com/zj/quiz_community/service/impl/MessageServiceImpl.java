package com.zj.quiz_community.service.impl;

import com.zj.quiz_community.dao.MessageDao;
import com.zj.quiz_community.pojo.Message;
import com.zj.quiz_community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 16:09
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;


    @Override
    public int addMessage(Message message) {
        return messageDao.addMessage(message);
    }

    @Override
    public List<Message> getConversationDetails(String conversationId, Integer offfset, Integer limit) {
        return messageDao.getConversationDetails(conversationId,offfset,limit);
    }

    @Override
    public List<Message> getConversationList(Integer userId, Integer offset, Integer limit) {
        return messageDao.getConversationList(userId,offset,limit);
    }

    @Override
    public Integer getConversationUnReadCount(Integer userId,String conversationId) {
        return messageDao.getConversationUnReadCount(userId,conversationId);
    }

    @Override
    public int updateAlreadyRead(String conversationId) {
        return messageDao.updateAlreadyRead(conversationId);
    }

    @Override
    public List<Message> isReadOrNot(String conversationId, Integer userId) {
        return messageDao.isReadOrNot(conversationId,userId);
    }

    @Override
    public int deleteMessage(String conversationId) {
        return messageDao.deleteMessage(conversationId);
    }
}
