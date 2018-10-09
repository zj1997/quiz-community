package com.zj.quiz_community.service;


import com.zj.quiz_community.pojo.User;

import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:16
 */
public interface UserService {

    User selectById(Integer id);

    Map<String,String> register(String username, String password);

    Map<String,String> login(String username, String password);

    void logout(String ticket);
}
