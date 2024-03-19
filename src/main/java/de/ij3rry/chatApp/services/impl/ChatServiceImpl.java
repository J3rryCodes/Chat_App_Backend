package de.ij3rry.chatApp.services.impl;

import de.ij3rry.chatApp.components.SendMessageComponent;
import de.ij3rry.chatApp.converters.DTOToDocumentConverter;
import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.documents.InboundMessageDocument;
import de.ij3rry.chatApp.documents.OnlineUserDocument;
import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;
import de.ij3rry.chatApp.dots.OutgoingMessageDTO;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import de.ij3rry.chatApp.repositories.InboundMessageRepository;
import de.ij3rry.chatApp.repositories.OnlineUserRepository;
import de.ij3rry.chatApp.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class ChatServiceImpl implements ChatService {

    private final SendMessageComponent sendMessageComponent;

    private final AppUserRepository appUserRepository;

    private final OnlineUserRepository onlineUserRepository;

    private final InboundMessageRepository inboundMessageRepository;

    @Autowired
    ChatServiceImpl(SendMessageComponent sendMessageComponent, OnlineUserRepository onlineUserRepository, InboundMessageRepository inboundMessageRepository, AppUserRepository appUserRepository){
        this.sendMessageComponent = sendMessageComponent;
        this.onlineUserRepository = onlineUserRepository;
        this.inboundMessageRepository = inboundMessageRepository;
        this.appUserRepository = appUserRepository;
    }
    @Override
    public void checkInUser(CheckInDTO checkInDTO) {

        // TODO : For now using a static . need to implement authentication system
        final UUID privateUserId = UUID.fromString("");
        /* fetch unsent messages from DB */
        final AppUserDocument appUserDocument = appUserRepository.findByPrivateID(privateUserId);
        final List<InboundMessageDocument> inboundMessageDocuments = inboundMessageRepository.findByToUserID(appUserDocument.getPublicID());
        /* send the message to the online user */
        if( inboundMessageDocuments !=null ){
            inboundMessageDocuments.forEach(document -> {
                final OutgoingMessageDTO outgoingMessageDTO = new OutgoingMessageDTO(document.getFromUserID(),document.getBody(),document.getType());
                sendMessageComponent.sendMessagesToUser(checkInDTO.getTopicId(),outgoingMessageDTO);
            });
        }
        /* then delete the messages which is delivered */
        inboundMessageRepository.deleteAll(inboundMessageDocuments);

        /* updating topic id for realtime transaction */
        appUserDocument.setTopicID(checkInDTO.getTopicId());
        appUserDocument.setOnline(true);
        appUserRepository.save(appUserDocument);
    }

    @Override
    public void handleIncomingMessage(IncomingMessageDTO incommingMessageDTO) {
        final AppUserDocument fromUser = appUserRepository.findByPrivateID(incommingMessageDTO.getPrivateUserID());
        if( fromUser == null )
            return;

        final OnlineUserDocument onlineUserDocument = onlineUserRepository.findByPublicUserId(incommingMessageDTO.getToUserID());
        /* check the user is available */
        if( onlineUserDocument != null && onlineUserDocument.isOnline() ){
            final OutgoingMessageDTO outgoingMessageDTO = new OutgoingMessageDTO(fromUser.getPublicID(),incommingMessageDTO.getBody(),incommingMessageDTO.getType());
            /* fetching the updated topicID */
            final AppUserDocument toUser = appUserRepository.findByPublicID(incommingMessageDTO.getToUserID());
            sendMessageComponent.sendMessagesToUser(toUser.getTopicID(),outgoingMessageDTO);
        }
        /* Store the messages for now */
        else{
            final InboundMessageDocument inboundMessageDocument = DTOToDocumentConverter.covertIncomingMessageDTOToDocument(incommingMessageDTO);
            inboundMessageDocument.setFromUserID(fromUser.getPublicID());
            inboundMessageRepository.save(inboundMessageDocument);
        }
    }
}
