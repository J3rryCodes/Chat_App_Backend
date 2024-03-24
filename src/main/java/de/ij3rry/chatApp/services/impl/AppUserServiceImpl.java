package de.ij3rry.chatApp.services.impl;

import de.ij3rry.chatApp.converters.DocumentsToDTOConverter;
import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.dots.SearchUserDetailsDTO;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import de.ij3rry.chatApp.services.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public SearchUserDetailsDTO searchUserWithUsername(String username) {
        AppUserDocument appUserDocument = appUserRepository.findByUsername(username);
        return DocumentsToDTOConverter.AppUserDocumentToSearchUserDetailsDTOConverter(appUserDocument);
    }
}
