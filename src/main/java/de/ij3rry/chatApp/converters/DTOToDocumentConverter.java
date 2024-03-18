package de.ij3rry.chatApp.converters;

import de.ij3rry.chatApp.documents.InboundMessageDocument;
import de.ij3rry.chatApp.dots.IncommingMessageDTO;

public class DTOToDocumentConverter {

    public static InboundMessageDocument covertIncomingMessageDTOToDocument(IncommingMessageDTO dto){
        final InboundMessageDocument document = new InboundMessageDocument();
        document.setToUserID(dto.getToUserID());
        document.setType(dto.getType());
        document.setBody(dto.getBody());
        return document;
    }
}
