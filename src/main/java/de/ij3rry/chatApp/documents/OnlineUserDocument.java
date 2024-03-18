package de.ij3rry.chatApp.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@Document
public class OnlineUserDocument {
    @MongoId
    private String id;
    private UUID publicUserId;
    private UUID topicId;
    private boolean online;
}
