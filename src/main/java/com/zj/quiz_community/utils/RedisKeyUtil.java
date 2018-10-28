package com.zj.quiz_community.utils;

/**
 * @author zhaojie
 * @date 2018\10\22 0022 - 16:07
 */
public class RedisKeyUtil {

    //喜欢点赞
    private static final String BIZ_LIKE = "LIKE";
    //不喜欢点踩
    private static final String BIZ_DISLIKE = "DISLIKE";
    //分隔符
    private static final String SPLIT = ":";
    //任务队列的key
    private static final String EVENTQUEUE="EVENTQUEUE";
    //关注的粉丝
    private static final String BIZ_FOLLOWER="FOLLOWER";
    //关注的对象
    private static final String BIZ_FOLLOWEE="FOLLOWEE";

    public static String getLikeKey(Integer entityType, Integer entityId){

        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getDisLikeKey(Integer entityType, Integer entityId){

        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getEVENTQUEUEKey(){

        return "EVENTQUEUE";
    }

    public static String getFollower(int entityType,int entityId){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);

    }

    public static String getFollowee(int userId,int entityType){
        return BIZ_FOLLOWEE+SPLIT+String.valueOf(userId)+SPLIT+String.valueOf(entityType);

    }



}
