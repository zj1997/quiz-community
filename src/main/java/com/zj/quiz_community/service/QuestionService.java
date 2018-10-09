package com.zj.quiz_community.service;


import com.zj.quiz_community.pojo.Question;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:17
 */
public interface QuestionService {

    List<Question> getLatestQuestion(Integer userId, Integer offset, Integer limit);

}
