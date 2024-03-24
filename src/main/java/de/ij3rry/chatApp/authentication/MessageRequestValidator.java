package de.ij3rry.chatApp.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@Slf4j
public class MessageRequestValidator {
    public static UUID getPrivateUserIDFromSIMPHeader(MessageHeaders messageHeaders){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) messageHeaders.get("simpUser");
        if(usernamePasswordAuthenticationToken == null || usernamePasswordAuthenticationToken.getPrincipal() == null)
            throw new UsernameNotFoundException("User not found!");
        return (UUID) usernamePasswordAuthenticationToken.getPrincipal();
    }
}
