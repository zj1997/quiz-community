package com.zj.quiz_community.service.impl;



import com.zj.quiz_community.dao.QuestionDao;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:19
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao dao;

    @Override
    public List<Question> getLatestQuestion(Integer userId, Integer offset, Integer limit) {
        return dao.getLatestQuestion(userId,offset,limit);
    }
}
