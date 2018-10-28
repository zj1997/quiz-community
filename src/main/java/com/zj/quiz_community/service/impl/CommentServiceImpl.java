package com.zj.quiz_community.service.impl;

import com.zj.quiz_community.dao.CommentDao;
import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 11:58
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;


    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public int deleteComment(Integer id) {
        return commentDao.updateStatus(id,1);
    }

    @Override
    public List<Comment> getByEntity(Integer entityId, Integer entityType) {
        return commentDao.getByEntity(entityId,entityType);
    }

    @Override
    public Integer getCommentCount(Integer entityId, Integer entityType) {
        return commentDao.getCommentCount(entityId,entityType);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentDao.getById(commentId);
    }

    @Override
    public Integer getCountByUserId(Integer userId) {
        return commentDao.getCountByUserId(userId);
    }
}
