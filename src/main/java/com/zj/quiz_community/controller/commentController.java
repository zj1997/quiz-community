package com.zj.quiz_community.controller;

import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.impl.CommentServiceImpl;
import com.zj.quiz_community.service.impl.SensitiveService;
import com.zj.quiz_community.utils.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.xml.ws.Holder;
import java.util.Date;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 12:07
 */
@Controller
public class commentController {

    private final Logger log = LoggerFactory.getLogger(commentController.class);

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(value = "/addComment",method = RequestMethod.POST )
    public String addContent( Model model,
                              @RequestParam("questionId") Integer questionId,
                              @RequestParam("content") String content){
        try{
            //敏感词过滤
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);

            //封装model
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);

            if(hostHolder.getUser() == null){
                comment.setUserId(MyUtil.ANONYMOUS_USERID);
            }else{
                comment.setUserId(hostHolder.getUser().getId());
            }

            commentService.addComment(comment);

            //更新题目评论数量
            Integer count = commentService.getCommentCount(questionId, EntityType.ENTITY_QUESTION);
            questionService.updateCommentCount(comment.getEntityId(),count);
            //异步化操作

        }catch (Exception e){
           log.error("增加评论出错"+e.getMessage());
        }

        return "redirect:/question/"+String.valueOf(questionId);
    }

}
