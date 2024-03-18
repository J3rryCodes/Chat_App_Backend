package de.ij3rry.chatApp.documents;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document(collection = "app_user")
@Data
public class AppUserDocument {
    @MongoId
    private String id;
    private UUID publicID;
    private UUID privateID;
    private UUID topicID;
    private boolean online;
}
