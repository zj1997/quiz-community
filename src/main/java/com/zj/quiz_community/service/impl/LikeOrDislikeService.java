package com.zj.quiz_community.service.impl;

import com.zj.quiz_community.utils.JedisUtil;
import com.zj.quiz_community.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaojie
 * @date 2018\10\22 0022 - 16:33
 */
@Service
public class LikeOrDislikeService {

    @Autowired
    private JedisUtil jedis;


    public long like(Integer userId,Integer entityType,Integer entityId){

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedis.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedis.srem(disLikeKey,String.valueOf(userId));

        return jedis.scard(likeKey);
    }

    public long dislike(Integer userId,Integer entityType,Integer entityId){

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedis.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedis.srem(likeKey,String.valueOf(userId));

        return jedis.scard(likeKey);  //此处易错  返回的始终是赞同的数量
    }


    public long getLikeCount(Integer entityType,Integer entityId){

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);

        return jedis.scard(likeKey);
    }


    public int likeStatus(Integer userId,Integer entityType,Integer entityId){

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedis.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedis.sismember(likeKey, String.valueOf(userId))?-1:0;

    }


}
