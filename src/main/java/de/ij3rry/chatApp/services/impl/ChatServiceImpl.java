package de.ij3rry.chatApp.services.impl;

import de.ij3rry.chatApp.authentication.MessageRequestValidator;
import de.ij3rry.chatApp.components.SendMessageComponent;
import de.ij3rry.chatApp.converters.DTOToDocumentConverter;
import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.documents.InboundMessageDocument;
import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;
import de.ij3rry.chatApp.dots.OutgoingMessageDTO;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import de.ij3rry.chatApp.repositories.InboundMessageRepository;
import de.ij3rry.chatApp.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public final class ChatServiceImpl implements ChatService {

    private final SendMessageComponent sendMessageComponent;

    private final AppUserRepository appUserRepository;


    private final InboundMessageRepository inboundMessageRepository;

    @Autowired
    ChatServiceImpl(SendMessageComponent sendMessageComponent, InboundMessageRepository inboundMessageRepository, AppUserRepository appUserRepository){
        this.sendMessageComponent = sendMessageComponent;
        this.inboundMessageRepository = inboundMessageRepository;
        this.appUserRepository = appUserRepository;
    }
    @Override
    public void checkInUser(CheckInDTO checkInDTO, MessageHeaders headers) {
        final UUID privateUserId = MessageRequestValidator.getPrivateUserIDFromSIMPHeader(headers);

        /* fetch unsent messages from DB */
        final AppUserDocument appUserDocument = appUserRepository.findByPrivateID(privateUserId);
        if( appUserDocument == null )
            throw new UsernameNotFoundException("App User not fund!");

        final List<InboundMessageDocument> inboundMessageDocuments = inboundMessageRepository.findByToUserID(appUserDocument.getPublicID());
        /* send the message to the online user */
        if( inboundMessageDocuments !=null && !inboundMessageDocuments.isEmpty()){
            inboundMessageDocuments.forEach(document -> {
                final OutgoingMessageDTO outgoingMessageDTO = new OutgoingMessageDTO(document.getFromUserID(),document.getBody(),document.getType());
                sendMessageComponent.sendMessagesToUser(checkInDTO.getTopicId(),outgoingMessageDTO);
            });
            /* then delete the messages which is delivered */
            inboundMessageRepository.deleteAll(inboundMessageDocuments);
        }

        /* updating topic id for realtime transaction */
        appUserDocument.setTopicID(checkInDTO.getTopicId());
        appUserDocument.setOnline(true);
        appUserRepository.save(appUserDocument);
    }

    @Override
    public void handleIncomingMessage(IncomingMessageDTO incomingMessageDTO, MessageHeaders headers) {
        final UUID privateUserId = MessageRequestValidator.getPrivateUserIDFromSIMPHeader(headers);
        final AppUserDocument fromUser = appUserRepository.findByPrivateID(privateUserId);
        if( fromUser == null )
            throw new UsernameNotFoundException("From User not fund!");

        /* check the user is available */
        final AppUserDocument toUser = appUserRepository.findByPublicID(incomingMessageDTO.getToUserId());
        if( toUser == null)
            throw new UsernameNotFoundException("To user not found!");
        if( toUser.isOnline() ){
            final OutgoingMessageDTO outgoingMessageDTO = new OutgoingMessageDTO(fromUser.getPublicID(),incomingMessageDTO.getBody(),incomingMessageDTO.getType());

            sendMessageComponent.sendMessagesToUser(toUser.getTopicID(),outgoingMessageDTO);
        }
        /* Store the messages for now */
        else{
            final InboundMessageDocument inboundMessageDocument = DTOToDocumentConverter.covertIncomingMessageDTOToDocument(incomingMessageDTO);
            inboundMessageDocument.setFromUserID(fromUser.getPublicID());
            inboundMessageRepository.save(inboundMessageDocument);
        }
    }
}
