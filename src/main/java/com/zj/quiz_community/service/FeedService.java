package com.zj.quiz_community.service;

import com.zj.quiz_community.pojo.Feed;
import com.zj.quiz_community.pojo.User;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\29 0029 - 21:33
 */
public interface FeedService {

    public boolean addFeed(Feed feed);

    public Feed getFeedById(Integer id);

    public List<Feed> selectUserFeeds(Integer maxId , List<Integer> userIds,Integer count);


}
