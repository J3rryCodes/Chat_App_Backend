package de.ij3rry.chatApp.repositories;

import de.ij3rry.chatApp.documents.InboundMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface InboundMessageRepository extends MongoRepository<InboundMessageDocument,String> {
    List<InboundMessageDocument> findByToUserID(UUID toUserID);
}
