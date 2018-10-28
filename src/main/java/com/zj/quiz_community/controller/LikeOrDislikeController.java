package com.zj.quiz_community.controller;

import com.alibaba.fastjson.JSONObject;
import com.zj.quiz_community.async.EventModel;
import com.zj.quiz_community.async.EventProducer;
import com.zj.quiz_community.async.EventType;
import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.service.CommentService;
import com.zj.quiz_community.service.impl.LikeOrDislikeService;
import com.zj.quiz_community.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author zhaojie
 * @date 2018\10\22 0022 - 16:49
 */
@Controller
public class LikeOrDislikeController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeOrDislikeService likeOrDislikeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(value = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") Integer commentId){

        if(hostHolder.getUser()==null){
            return MyUtil.getJsonObject(999);
        }

        Comment comment = commentService.getCommentById(commentId);

        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
                .setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityOwerId(comment.getUserId()).setExt("questionId",String.valueOf(commentId))
        );

        long likeCount = likeOrDislikeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);


        return MyUtil.getJsonObject(0,String.valueOf(likeCount));
    }


    @RequestMapping(value = "/dislike",method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") Integer commentId){

        if(hostHolder.getUser()==null){
            return MyUtil.getJsonObject(999);
        }

        Comment comment = commentService.getCommentById(commentId);

        long disLikeCount = likeOrDislikeService.dislike(hostHolder.getUser().getId(),  EntityType.ENTITY_COMMENT,commentId);


        return MyUtil.getJsonObject(0,String.valueOf(disLikeCount));
    }


}
