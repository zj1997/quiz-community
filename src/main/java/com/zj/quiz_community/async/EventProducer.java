package com.zj.quiz_community.async;

import com.alibaba.fastjson.JSONObject;
import com.zj.quiz_community.utils.JedisUtil;
import com.zj.quiz_community.utils.MyUtil;
import com.zj.quiz_community.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 20:40
 */
@Service
public class EventProducer  {

    @Autowired
    private JedisUtil jedis;

    private Logger log = LoggerFactory.getLogger(EventProducer.class);

    public boolean fireEvent(EventModel eventModel){

        try{

            String json = JSONObject.toJSONString(eventModel);

            String eventqueueKey = RedisKeyUtil.getEVENTQUEUEKey();

            jedis.lpush(eventqueueKey,json);

            return true;

        }catch (Exception e){
            log.error("插入任务队列失败！");
            return false;
        }
    }

}
