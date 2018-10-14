package com.zj.quiz_community.service;

import com.zj.quiz_community.pojo.Comment;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 11:55
 */
public interface CommentService {


    int addComment(Comment comment);

    //待定中。。。
    int deleteComment(Integer id);

    List<Comment> getByEntity(Integer entityId,Integer entityType);

    Integer getCommentCount(Integer entityId,Integer entityType);


}
