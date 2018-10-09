package com.zj.quiz_community.dto;

import com.zj.quiz_community.pojo.User;
import org.springframework.stereotype.Component;

/**
 * @author zhaojie
 * @date 2018\10\8 0008 - 23:34
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> local = new ThreadLocal<User>();

    public User getUser(){

        return local.get();
    }

    public void setUser(User user){

         local.set(user);
    }

    public void clear(){

        local.remove();
    }



}
