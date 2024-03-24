package de.ij3rry.chatApp.configurations;

import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;

@Slf4j
@Component
public class STOMPDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {
    private final AppUserRepository appUserRepository;

    STOMPDisconnectHandler(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) event.getUser();
        UUID userID = (UUID) user.getPrincipal();

        AppUserDocument appUserDocument = appUserRepository.findByPrivateID(userID);
        appUserDocument.setOnline(false);
        appUserDocument.setTopicID(null);
        appUserRepository.save(appUserDocument);

        log.info("User disconnected : {}",appUserDocument.getUsername());
    }
}
