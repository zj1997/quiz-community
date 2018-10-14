package com.zj.quiz_community.service.impl;



import com.zj.quiz_community.dao.QuestionDao;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:19
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private SensitiveService sensitiveService;

    public List<Question> getLatestQuestion(Integer userId, Integer offset, Integer limit) {
        return questionDao.getLatestQuestion(userId,offset,limit);
    }


    public int addQuestion(Question question) {

        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        //敏感词过滤操作

        return questionDao.addQuestion(question) > 0 ? question.getId():0;
    }


    public Question getQuestionById(Integer id) {

        Question question = questionDao.getById(id);

        return question;
    }

    @Override
    public int updateCommentCount(Integer userId, Integer comment_count) {
        return questionDao.updateCommentCount(userId,comment_count);
    }
}
