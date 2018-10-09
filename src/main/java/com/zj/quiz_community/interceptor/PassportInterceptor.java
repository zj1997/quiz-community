package com.zj.quiz_community.interceptor;

import com.zj.quiz_community.dao.LoginTicketDao;
import com.zj.quiz_community.dao.UserDao;
import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.pojo.LoginTicket;
import com.zj.quiz_community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhaojie
 * @date 2018\10\8 0008 - 23:19
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        String ticket = null;

        if(cookies!=null){

            for(Cookie cookie:cookies){

                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }

            if(ticket!=null){

                LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);

                //进行判断cookie是否有效
                if(loginTicket == null || loginTicket.getStatus()!=0 || loginTicket.getExpired().before(new Date())){

                    return true;

                }

                User user = userDao.selectById(loginTicket.getUserId());

                hostHolder.setUser(user);
            }

        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        User user = hostHolder.getUser();

        if(modelAndView != null && user!=null){

            modelAndView.addObject("user",user);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if(hostHolder.getUser()!=null){
            hostHolder.clear();
        }

    }
}
