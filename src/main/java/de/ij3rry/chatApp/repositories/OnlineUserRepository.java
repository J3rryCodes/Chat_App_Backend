package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.OnlineUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OnlineUserRepository extends MongoRepository<OnlineUserDocument, String> {
    OnlineUserDocument findByPublicUserId(UUID fromUerId);
}
