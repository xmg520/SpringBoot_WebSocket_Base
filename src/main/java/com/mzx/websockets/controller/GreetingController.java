package com.mzx.websockets.controller;

import com.mzx.websockets.pojo.Chat;
import com.mzx.websockets.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingController {

    // 第一种群发方式
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception{
        return message;
    }
    // 第二种群发方式
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello1")
    public void greeting1(Message message) throws Exception{
        messagingTemplate.convertAndSend("/topic/greetings",message);
    }

    // 点对点
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat){
        String from = principal.getName();
        chat.setFrom(from);
        messagingTemplate.convertAndSendToUser(chat.getTo(),"/queue/chat",chat);
    }

}
