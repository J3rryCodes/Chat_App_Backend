package de.ij3rry.chatApp.controllers;

import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;
import de.ij3rry.chatApp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void receiveMessage(IncomingMessageDTO incomingMessageDTO){
        chatService.handleIncomingMessage(incomingMessageDTO);
    }

    @MessageMapping("/check-in")
    public void checkInUser(CheckInDTO checkInDTO){
        chatService.checkInUser(checkInDTO);
    }
}
