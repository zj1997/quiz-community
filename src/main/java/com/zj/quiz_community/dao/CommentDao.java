package com.zj.quiz_community.dao;

import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 11:33
 */
@Mapper
public interface CommentDao {


    String INSERT_FIELD=" user_id,content,entity_id,entity_type,status ,created_date ";
    String TABLE_NAME="comment";
    String SELECT_FIELD=" id,"+INSERT_FIELD;


    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{userId},#{content},#{entityId},#{entityType},#{status},#{createdDate})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FIELD,"from ",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
    List<Comment> getByEntity(@Param("entityId") Integer entityId,@Param("entityType") Integer entityType);

    @Update({"update ",TABLE_NAME," set status= #{status} where id = #{id}"})
    int updateStatus( @Param("id") Integer id,@Param("status") Integer status);

    @Select({"select count(id) from ",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType}"})
    Integer getCommentCount(@Param("entityId") Integer entityId,@Param("entityType") Integer entityType);


}
