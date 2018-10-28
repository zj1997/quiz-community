package com.zj.quiz_community.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * @author zhaojie
 * @date 2018\10\20 0020 - 16:43
 */
@Component
public class JedisUtil implements InitializingBean{

    private Logger log = LoggerFactory.getLogger(JedisUtil.class);

    private JedisPool jedisPool;


    @Override
    public void afterPropertiesSet() throws Exception {

        jedisPool = new JedisPool("192.168.195.133",6379);

    }

    public long sadd(String key , String...members){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.sadd(key, members);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key , String...members){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.srem(key, members);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.scard(key);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key , String value){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.sismember(key, value);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return false;
    }

    public void lpush(String key , String value){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            jedis.lpush(key, value);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public List<String> brpop(String key){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            List<String> list = jedis.brpop(0, key);

            return list;

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key,double score,String value){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.zadd(key, score, value);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public long zrem(String key,String value){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.zrem(key,value);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Jedis getJedis(){

        Jedis jedis = null;

        try {

            return jedisPool.getResource();

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public Transaction multi(Jedis jedis){

        try {

             return jedis.multi();

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public  List<Object>  exec(Transaction tx,Jedis jedis) throws IOException {

        try {

              return tx.exec();

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
            tx.discard();
        }finally {

            if(tx!=null){
                tx.close();
            }

        }
        return null;
    }

    public Set<String> zrange(String key ,int start , int end)  {

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

           return jedis.zrange(key, start, end);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
           if(jedis!=null){
               jedis.close();
           }
        }
        return null;
    }

    public  Set<String>  zrevrange(String key ,int start , int end)  {

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.zrevrange(key, start, end);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }


    public long zcard(String key){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.zcard(key);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key,String member){

        Jedis jedis = null;

        try {

            jedis = jedisPool.getResource();

            return jedis.zscore(key,member);

        }catch (Exception e){
            log.error("操作失败",e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return Double.valueOf(0);
    }


}
