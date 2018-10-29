package com.zj.quiz_community.service.impl;

import com.zj.quiz_community.dao.FeedDao;
import com.zj.quiz_community.pojo.Feed;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\29 0029 - 21:37
 */
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedDao feedDao;

    @Override
    public boolean addFeed(Feed feed) {

        feedDao.addFeed(feed);

        return feed.getId() >0;
    }

    @Override
    public Feed getFeedById(Integer id) {
        return feedDao.getFeedById(id);
    }

    @Override
    public List<Feed> selectUserFeeds(Integer maxId, List<Integer> userIds, Integer count) {
        return feedDao.selectUserFeeds(maxId,userIds,count);
    }
}
