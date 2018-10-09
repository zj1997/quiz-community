package com.zj.quiz_community.controller;


import com.zj.quiz_community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\7 0007 - 16:50
 */
@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoggerFactory.class);

     @Autowired
     private UserService userService;


    @RequestMapping(value = "/reg/" ,method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next",required = false) String next,
                           HttpServletResponse response){


        Map<String, String> map = userService.register(username, password);


        try {

            if(map.containsKey("ticket")){

                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);

                if(!StringUtils.isEmpty(next)){
                    return "redirect:"+next;
                }

                return "redirect:/";
            }else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }


        }catch (Exception e){

            log.error("注册异常！"+e.getMessage());
             model.addAttribute("msg","服务器错误");
             return "login";
        }
    }



    @RequestMapping(value = "/login/" ,method = RequestMethod.POST)
    public String login(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next",required = false) String next,
                           HttpServletResponse response){


        Map<String, String> map = userService.login(username, password);

        try {

            if(map.containsKey("ticket")){

                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);

                if(!StringUtils.isEmpty(next)){
                    return "redirect:"+next;
                }

                return "redirect:/";

            }else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }


        }catch (Exception e){

            log.error("注册异常！"+e.getMessage());
            model.addAttribute("msg","服务器错误");
            return "login";
        }
    }




    @RequestMapping(value = "/reglogin", method=RequestMethod.GET)
    public String regLoginPage(@RequestParam("next") String next,Model model){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(value = {"/logout"},method = RequestMethod.GET)
    public String logout(@CookieValue(value = "ticket",required = false) String ticket){

        if(ticket!=null){
            userService.logout(ticket);
        }
        return "redirect:/";
    }

}
