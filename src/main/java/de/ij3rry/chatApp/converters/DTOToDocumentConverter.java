package de.ij3rry.chatApp.converters;

import de.ij3rry.chatApp.documents.InboundMessageDocument;
import de.ij3rry.chatApp.dots.IncomingMessageDTO;

public class DTOToDocumentConverter {

    public static InboundMessageDocument covertIncomingMessageDTOToDocument(IncomingMessageDTO dto){
        final InboundMessageDocument document = new InboundMessageDocument();
        document.setToUserID(dto.getToUserId());
        document.setType(dto.getType());
        document.setBody(dto.getBody());
        return document;
    }
}
