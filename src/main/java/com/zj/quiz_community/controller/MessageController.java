package com.zj.quiz_community.controller;

import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.dto.ViewObject;
import com.zj.quiz_community.pojo.Message;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.MessageService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.utils.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\13 0013 - 16:10
 */
@Controller
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/msg/addMessage",method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){

        try{

            Message message = new Message();

            message.setContent(content);
            message.setCreatedDate(new Date());

            if(hostHolder == null){
                MyUtil.getJsonObject(1,"用户未登陆");
            }else{
                message.setFromId(hostHolder.getUser().getId());
            }

            User user = userService.selectByName(toName);

            if(user == null){
                return MyUtil.getJsonObject(1,"用户不存在！");
            }

            message.setToId(user.getId());


            int isSuccess = messageService.addMessage(message);


                 return isSuccess==0? MyUtil.getJsonObject(0,"发送信息失败！"):MyUtil.getJsonObject(0);

        }catch (Exception e){
            log.error("插入信息失败"+e.getMessage());
            return MyUtil.getJsonObject(1,"发送信息失败！");
        }
    }


    @RequestMapping(value = "/msg/detail",method = RequestMethod.GET)
    public String conversationDetail(Model model,@RequestParam("conversationId") String conversationId){


        try{

            List<Message> UnReadMessageList = messageService.isReadOrNot(conversationId, hostHolder.getUser().getId());

            //当前用户是接受者，进入详情页面，未读信息清零。
            if(UnReadMessageList.size() !=0 ){
                messageService.updateAlreadyRead(conversationId);
            }

                List<Message> conversationDetails = messageService.getConversationDetails(conversationId, 0, 10);

                List<ViewObject> vos = new ArrayList<>();

                for (Message message:conversationDetails){

                    ViewObject vo = new ViewObject();

                    vo.set("message",message);
                    User user = userService.selectById(message.getFromId());

                    if( user == null){
                        continue;
                    }

                    vo.set("headUrl",user.getHeadUrl());
                    vo.set("userId",user.getId());

                    vos.add(vo);
                }

                model.addAttribute("messages",vos);


        }catch (Exception e){
            log.error("发送信息失败！");
        }

      return "letterDetail";
    }


    @RequestMapping(value = "/msg/list",method = RequestMethod.GET)
    public String conversationList(Model model){

        User user = hostHolder.getUser();

        if(user == null){
            return "redirect:/reglogin";
        }

        List<Message> messages = messageService.getConversationList(user.getId(), 0, 10);

        List<ViewObject> vos = new ArrayList<>();

        for (Message message:messages){

            ViewObject vo = new ViewObject();

            vo.set("conversation",message);

            //判断消息列表页面的用户信息是发送者还是接受者
            int targetId = user.getId() == message.getFromId()?message.getToId():message.getFromId();

            User targetUser = userService.selectById(targetId);

            vo.set("user",targetUser);

            vo.set("unread",messageService.getConversationUnReadCount(user.getId(),message.getConversationId()));

            vos.add(vo);
        }

        model.addAttribute("conversations",vos);

        return "letter";
    }

    @RequestMapping(value = "/msg/delete",method = RequestMethod.GET)
    public String deleteMessage(@RequestParam("conversationId") String conversationId){

        int isSuccess = messageService.deleteMessage(conversationId);

        if(isSuccess >0){
            return "redirect:/msg/list";
        }
        return null;
    }

}
