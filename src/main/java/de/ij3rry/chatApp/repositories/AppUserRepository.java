package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends MongoRepository<AppUserDocument,String> {
    AppUserDocument findByPrivateID(UUID privateUserID);

    AppUserDocument findByPublicID(UUID toUserID);

    AppUserDocument findByUsername(String username);
}
