package com.zj.quiz_community.service;

import com.zj.quiz_community.pojo.Message;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 16:07
 */
public interface MessageService {

    int addMessage(Message message);

    List<Message> getConversationDetails(String conversationId, Integer offfset, Integer limit);

    List<Message> getConversationList(Integer userId,Integer offset,Integer limit);

    Integer getConversationUnReadCount(Integer userId,String conversationId);

    int updateAlreadyRead(String conversationId);

    List<Message> isReadOrNot(String conversationId,Integer userId);

    int deleteMessage(String conversationId);

}
