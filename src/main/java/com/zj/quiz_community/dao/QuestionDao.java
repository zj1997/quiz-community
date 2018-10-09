package com.zj.quiz_community.dao;


import com.zj.quiz_community.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 22:39
 */
@Mapper
public interface QuestionDao {

    //注意抽取的关键字的空格
    String INSERT_FILED=" title,content,user_id,created_date,comment_count ";
    String TABLE_NAME="question";
    String SELECT_FILED=" id,"+INSERT_FILED;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FILED,") values(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> getLatestQuestion(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
