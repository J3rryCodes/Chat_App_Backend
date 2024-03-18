package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AppUserRepository extends MongoRepository<AppUserDocument,String> {
    AppUserDocument findByPrivateID(UUID privateUserID);

    AppUserDocument findByPublicID(UUID toUserID);
}
