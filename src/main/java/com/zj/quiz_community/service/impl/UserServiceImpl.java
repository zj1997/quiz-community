package com.zj.quiz_community.service.impl;


import com.zj.quiz_community.dao.LoginTicketDao;
import com.zj.quiz_community.dao.UserDao;
import com.zj.quiz_community.pojo.LoginTicket;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:22
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Override
    public User selectById(Integer id) {
       return dao.selectById(id);
    }


    @Transactional
    public Map<String, String> register(String username, String password) {

        Map<String, String> map = new HashMap<>();


        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空！");
            return map;
        }

        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空！");
            return map;
        }

        User user = dao.selectByName(username);

        if(user!=null){
            map.put("msg","用户名已存在，请重新输入！");
            return map;
        }

        //密码强度增加 ， 开始阶段用户头像系统随机生成
        User u = new User();

        u.setName(username);
        u.setSalt(UUID.randomUUID().toString().substring(0,5));

        String head = String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000));
        u.setHeadUrl(head);
        u.setPassword(MyUtil.MD5(password+u.getSalt()));

        dao.addUser(u);

        String ticket = addLoginTicket(u.getId());
        map.put("ticket",ticket);

        return map;
    }

    @Transactional
    public Map<String, String> login(String username, String password) {

        Map<String, String> map = new HashMap<>();


        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空！");
            return map;
        }

        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空！");
            return map;
        }

        User user = dao.selectByName(username);

        if(user == null){
            map.put("msg","用户名不存在！");
            return map;
        }

        if(!MyUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码输入错误！");
            return map;
        }

        String ticket = addLoginTicket(user.getId());

        map.put("ticket",ticket);

        return map;
    }


    public String addLoginTicket(Integer userId){


        LoginTicket loginTicket =  new LoginTicket();
        String ticket = null;

        loginTicket.setUserId(userId);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicket.setStatus(0);
        Date now = new Date();
        now.setTime(now.getTime()+3600*24*100);
        loginTicket.setExpired(now);

        int isSuccess = loginTicketDao.addLoginTicket(loginTicket);

        if(isSuccess!=0)
        {
           ticket = loginTicket.getTicket();
        }
        return ticket;
    }


    public void logout(String ticket){
        loginTicketDao.updateStatus(1,ticket);
    }


}
