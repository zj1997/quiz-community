package com.zj.quiz_community.utils;

import redis.clients.jedis.Jedis;

/**
 * @author zhaojie
 * @date 2018\10\28 0028 - 18:39
 */
public class JedisUtilTest{



    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.195.133",6379);

         jedis.zadd("hello",15,"小明");
         jedis.zadd("hello",15,"小r");
         jedis.zadd("hello",15,"小g");
         jedis.zadd("hello",15,"小w");


    }


}
