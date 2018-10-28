package com.zj.quiz_community.controller;

import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.dto.ViewObject;
import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.CommentService;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.service.impl.LikeOrDislikeService;
import com.zj.quiz_community.service.impl.followService;
import com.zj.quiz_community.utils.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\9 0009 - 21:58
 */
@Controller
public class QuestionController {

    private final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeOrDislikeService likeOrDislikeService;

    @Autowired
    private followService followService;

    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title , @RequestParam("content") String content){

        Question question = new Question();

        question.setContent(content);
        question.setTitle(title);
        question.setCommentCount(0);
        question.setCreatedDate(new Date());

        if(hostHolder == null){

            question.setUserId(MyUtil.ANONYMOUS_USERID);
        }else {
            question.setUserId(hostHolder.getUser().getId());
        }

        try {

            int isSuccess = questionService.addQuestion(question);

            if(isSuccess!=0){
                return MyUtil.getJsonObject(0);
            }

        }catch (Exception e){

            log.error("问题提交失败"+e.getMessage());

        }

        return MyUtil.getJsonObject(1,"问题提交失败！");

    }

    @RequestMapping(value = "/question/{qid}",method = RequestMethod.GET)
    public String questionDetail(Model model , @PathVariable("qid") Integer qid){

        Question question = questionService.getQuestionById(qid);

        model.addAttribute("question",question);

        List<Comment> comments = commentService.getByEntity(qid, EntityType.ENTITY_QUESTION);

        List<ViewObject> vos = new ArrayList<>();

        for(Comment comment:comments){

            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser()==null){
                vo.set("liked",0);
            }else{
                vo.set("liked",likeOrDislikeService.likeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }

            vo.set("likeCount",likeOrDislikeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));

            vo.set("user",userService.selectById(comment.getUserId()));

            vos.add(vo);
        }

        model.addAttribute("comments",vos);

        //获取关注的用户信息
        List<Integer> users = followService.getfollowers(EntityType.ENTITY_QUESTION, qid, 20);

        if(users!=null){
            List<ViewObject> followerUsers = new ArrayList<>();

            for(Integer userId:users){

                ViewObject vo = new ViewObject();

                User user = userService.selectById(userId);

                if(user==null){
                    continue;
                }

                vo.set("name",user.getName());
                vo.set("headUrl",user.getHeadUrl());
                vo.set("id",user.getId());

                followerUsers.add(vo);
            }

            model.addAttribute("followUsers",followerUsers);
        }



        if(hostHolder.getUser()!=null){
            model.addAttribute("followed",followService.isFollower(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,qid));
        }else {
            model.addAttribute("followed",false);
        }

        return "detail";
    }

}
