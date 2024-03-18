package de.ij3rry.chatApp.components;

import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.dots.OutgoingMessageDTO;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendMessageComponent {

    private SimpMessagingTemplate messagingTemplate;
    private AppUserRepository appUserRepository;

    @Autowired
    SendMessageComponent(SimpMessagingTemplate messagingTemplate,AppUserRepository appUserRepository){
        this.messagingTemplate = messagingTemplate;
        this.appUserRepository = appUserRepository;
    }
    public void sendMessagesToUser(UUID userId, OutgoingMessageDTO message){
        messagingTemplate.convertAndSendToUser(userId.toString(),"/topic/messages",message);
        AppUserDocument c = new AppUserDocument();
        c.setPrivateID(UUID.randomUUID());
        c.setPublicID(UUID.randomUUID());
        AppUserDocument appUserDocument = appUserRepository.save(c);
    }
}
