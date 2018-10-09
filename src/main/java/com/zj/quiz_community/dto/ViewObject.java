package com.zj.quiz_community.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:26
 */
public class ViewObject {

    private Map<String ,Object> map = new HashMap<>();

    public void set(String key,Object value){

        map.put(key,value);
    }

    public Object get(String key){
        return map.get(key);
    }

}
