package de.ij3rry.chatApp.controllers;

import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;
import de.ij3rry.chatApp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    private ChatService chatService;

    @Autowired
    MessagingController(ChatService chatService){
        this.chatService = chatService;
    }
    @MessageMapping("/sentMessage")
    public void receiveMessage(IncomingMessageDTO incomingMessageDTO, MessageHeaders headers){
        chatService.handleIncomingMessage(incomingMessageDTO,headers);
    }

    @MessageMapping("/check-in")
    public void checkInUser(CheckInDTO checkInDTO, MessageHeaders headers){
        chatService.checkInUser(checkInDTO, headers);
    }
}