package com.zj.quiz_community.dao;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 15:57
 */
@Mapper
public interface MessageDao {


    String INSERT_FIELD=" from_id,to_id,content,created_date,has_read,conversation_id,status";
    String TABLE_NAME="message";
    String SELECT_FIELD=" id,"+INSERT_FIELD;


    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId},0)"})
    int addMessage(Message message);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where conversation_id=#{conversationId} and status!=1 order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationDetails(@Param("conversationId") String conversationId,
                                         @Param("offset") Integer offset,
                                         @Param("limit") Integer limit);


    @Select({"select ",INSERT_FIELD," ,count(id) as id from (select * from message where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by " +
            "conversation_id having status !=1 order by created_date limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") Integer userId,@Param("offset") Integer offset,@Param("limit") Integer limit);

    @Select({"select count(id) from ",TABLE_NAME," where status!=1 and has_read= 0 and to_id=#{userId} and  conversation_id=#{conversationId}"})
    Integer getConversationUnReadCount(@Param("userId") Integer userId,@Param("conversationId") String conversationId);

    @Update({"update ",TABLE_NAME," set has_read =1 where status!=1 and conversation_id=#{conversationId}"})
    int updateAlreadyRead(String conversationId);

    @Select({"select ",SELECT_FIELD," from message where status!=1 and has_read = 0 and conversation_id=#{conversationId} and to_id=#{userId}"})
    List<Message> isReadOrNot(@Param("conversationId") String conversationId,@Param("userId") Integer userId);

    @Update({"update message set status=1 where conversation_id=#{conversationId}"})
    int deleteMessage(String conversationId);


}
