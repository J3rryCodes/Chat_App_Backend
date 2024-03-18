package de.ij3rry.chatApp.dots;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OutgoingMessageDTO {
    private UUID fromUserId;
    private Object body;
    private String type;
}
