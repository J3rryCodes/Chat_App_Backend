package de.ij3rry.chatApp.converters;

import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.dots.SearchUserDetailsDTO;

public class DocumentsToDTOConverter {

    public static SearchUserDetailsDTO AppUserDocumentToSearchUserDetailsDTOConverter(AppUserDocument appUserDocument){
        SearchUserDetailsDTO searchUserDetailsDTO = new SearchUserDetailsDTO();
        searchUserDetailsDTO.setPublicID(appUserDocument.getPublicID());
        searchUserDetailsDTO.setUsername(appUserDocument.getUsername());
        return searchUserDetailsDTO;
    }
}
