package de.ij3rry.chatApp.services;

import de.ij3rry.chatApp.dots.CheckInDTO;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;

public interface ChatService {
    void checkInUser(CheckInDTO checkInDTO);

    void handleIncomingMessage(IncomingMessageDTO incommingMessageDTO);
}
