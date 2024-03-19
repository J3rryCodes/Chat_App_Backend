package de.ij3rry.chatApp.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@Document("inbound_message")
public class InboundMessageDocument {
    @MongoId
    private String id;
    private UUID fromUserID;
    private UUID toUserID;
    private String type;
    private Object body;
}
