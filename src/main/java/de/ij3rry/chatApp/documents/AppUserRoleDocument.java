package de.ij3rry.chatApp.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.UUID;

@Data
@Document("app_user_role")
public class AppUserRoleDocument {
    @MongoId
    private String id;
    private UUID privateId;
    List<String> userRole;
}
