package com.zj.quiz_community.service.impl;


import com.zj.quiz_community.QuizCommunityApplicationTest;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\8 0008 - 19:59
 */
public class UserServiceImplTest extends QuizCommunityApplicationTest {

    @Autowired
    private UserService service;

    @Autowired
    private QuestionService questionService;


    @Test
    public void selectById() {

        List<Question> list = questionService.getLatestQuestion(0, 0, 10);

        for(Question q:list){

            System.out.println(q);

            User user = service.selectById(q.getUserId());

            System.out.println(user);
        }



    }
}