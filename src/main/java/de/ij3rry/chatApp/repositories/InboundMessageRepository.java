package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.InboundMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InboundMessageRepository extends MongoRepository<InboundMessageDocument,String> {
    List<InboundMessageDocument> findByToUserID(UUID toUserID);
}
