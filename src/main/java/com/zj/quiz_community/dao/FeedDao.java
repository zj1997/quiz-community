package com.zj.quiz_community.dao;

import com.zj.quiz_community.pojo.Comment;
import com.zj.quiz_community.pojo.Feed;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 11:33
 */
@Mapper
public interface FeedDao {


    String INSERT_FIELD=" user_id,type,data,created_date ";
    String TABLE_NAME="feed";
    String SELECT_FIELD=" id,"+INSERT_FIELD;


    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{userId},#{type},#{data},#{createdDate})"})
    int addFeed(Feed feed);

    @Select({"select ",SELECT_FIELD,"from ",TABLE_NAME,"where id=#{id}"})
    Feed getFeedById(Integer id);


    List<Feed> selectUserFeeds(@Param("maxId") Integer maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") Integer count);

}
