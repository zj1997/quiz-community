package com.zj.quiz_community.service.impl;

import com.zj.quiz_community.utils.JedisUtil;
import com.zj.quiz_community.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojie
 * @date 2018\10\28 0028 - 15:13
 */
//关注与被关注业务
@Service
public class followService {

    @Autowired
    JedisUtil jedisUtil;


    /**
     * 用户关注了某个实体 可以使用户 问题  评论
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     * @throws IOException
     */
    public boolean follow(int userId,int entityType , int entityId) throws IOException {

        String followerKey = RedisKeyUtil.getFollower(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFollowee(userId, entityType);

        Date date = new Date();
        Jedis jedis = jedisUtil.getJedis();
        Transaction tx = jedis.multi();
        //实体的粉丝增加为当前的用户
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        //当前用户的关注为实体
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));

        List<Object> exes = tx.exec();

        return exes.size() == 2 && (Long)exes.get(0)>0 && (Long)exes.get(1)>0;

    }

    /**
     * 取消关注
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     * @throws IOException
     */

    public boolean unfollow(int userId,int entityType , int entityId) throws IOException {

        String followerKey = RedisKeyUtil.getFollower(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFollowee(userId, entityType);

        Jedis jedis = jedisUtil.getJedis();
        Transaction tx = jedis.multi();

        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));

        List<Object> exes = tx.exec();

        return exes.size() == 2 && (long)exes.get(0)>0 && (long)exes.get(1)>0 ;

    }


    public List<Integer> getfollowers(int entityType , int entityId, int count){

        String follower = RedisKeyUtil.getFollower(entityType, entityId);

        return getIdsFromSet(jedisUtil.zrange(follower, 0, count));

    }

    public List<Integer> getfollowers(int entityType , int entityId,int offset , int count){

        String follower = RedisKeyUtil.getFollower(entityType, entityId);
        return getIdsFromSet(jedisUtil.zrange(follower,offset,count+offset));
    }

    public List<Integer> getfollowees(int userId, int entityType, int count){

        String followee = RedisKeyUtil.getFollowee(userId,entityType);
        return getIdsFromSet(jedisUtil.zrange(followee,0,count));
    }

    public List<Integer> getfollowees(int userId, int entityType, int offset,int count){

        String followee = RedisKeyUtil.getFollowee(userId,entityType);
        return getIdsFromSet(jedisUtil.zrange(followee,offset,count+offset));
    }

    public Long getFollowerCount(int entityType, int entityId){

        String follower = RedisKeyUtil.getFollower(entityType, entityId);
        return jedisUtil.zcard(follower);
    }

    public Long getFolloweeCount(int userId, int entityType){

        String followee = RedisKeyUtil.getFollowee(userId,entityType);
        return jedisUtil.zcard(followee);
    }

    public List<Integer> getIdsFromSet(Set<String> sets){

        List<Integer> list = new ArrayList<>();

        for(String set : sets){

            list.add(Integer.valueOf(set));

        }

        return list;
    }

    /**
     * 判断是否关注了某个实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */

    public boolean isFollower(int userId,int entityType,int entityId){

        String follower = RedisKeyUtil.getFollower(entityType, entityId);
        return jedisUtil.zscore(follower,String.valueOf(userId))!=null;
    }

}
