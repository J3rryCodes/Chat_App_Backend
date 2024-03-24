package de.ij3rry.chatApp.dots;

import lombok.Data;

import java.util.UUID;

@Data
public class IncomingMessageDTO {
    private UUID toUserId;
    private String type;
    private Object body;
}
