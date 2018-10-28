package com.zj.quiz_community.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.quiz_community.utils.JedisUtil;
import com.zj.quiz_community.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\23 0023 - 20:55
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{

    private Map<EventType,List<EventHandler>> config = new HashMap<>();

    private Logger log = LoggerFactory.getLogger(EventProducer.class);

    private ApplicationContext applicationContext;

    @Autowired
    private JedisUtil jedisUtil;


    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if(beans!=null){

            for(Map.Entry<String,EventHandler> entry: beans.entrySet()){

                List<EventType> typeList = entry.getValue().getSupportEventType();

                for(EventType type : typeList){

                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }

                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){

                    String key = RedisKeyUtil.getEVENTQUEUEKey();

                    List<String> evens = jedisUtil.brpop(key);

                    for(String message: evens){

                      if(message.contains(key)){
                          continue;
                      }

                      EventModel model = JSON.parseObject(message, EventModel.class);

                      if(!config.containsKey(model.getEventType())){
                          log.error("不能识别的事件");
                          continue;
                      }

                      List<EventHandler> handlers = config.get(model.getEventType());

                      for(EventHandler handler : handlers){

                          handler.doHandler(model);

                      }
                    }
                }
            }
        });

        th.start();
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
