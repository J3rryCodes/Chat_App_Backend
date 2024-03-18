package de.ij3rry.chatApp.dots;

import lombok.Data;

import java.util.UUID;

@Data
public class IncommingMessageDTO {
    private UUID toUserID;
    private UUID privateUserID;
    private String type;
    private Object body;
}
