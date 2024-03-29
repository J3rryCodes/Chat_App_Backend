package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.AppUserRoleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRoleRepository extends MongoRepository<AppUserRoleDocument,String> {
    AppUserRoleDocument findByPrivateId(UUID username);
}
