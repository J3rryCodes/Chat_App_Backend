package de.ij3rry.chatApp.services;

import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;
import org.springframework.messaging.MessageHeaders;

public interface ChatService {
    void checkInUser(CheckInDTO checkInDTO, MessageHeaders headers);

    void handleIncomingMessage(IncomingMessageDTO incomingMessageDTO,MessageHeaders headers);
}
