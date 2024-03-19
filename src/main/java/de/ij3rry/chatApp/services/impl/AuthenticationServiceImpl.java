package de.ij3rry.chatApp.services.impl;

import de.ij3rry.chatApp.components.JWTComponent;
import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.documents.AppUserRoleDocument;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import de.ij3rry.chatApp.repositories.AppUserRoleRepository;
import de.ij3rry.chatApp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JWTComponent jwtComponent;

    private final PasswordEncoder passwordEncode;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final AppUserRepository appUserRepository;

    private final AppUserRoleRepository appUserRoleRepository;

    @Autowired
    AuthenticationServiceImpl(AuthenticationManager authenticationManager, JWTComponent jwtComponent, PasswordEncoder passwordEncode,AuthenticationManagerBuilder authenticationManagerBuilder,UserDetailsService userDetailsService,AppUserRepository appUserRepository,AppUserRoleRepository appUserRoleRepository,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtComponent = jwtComponent;
        this.passwordEncode = passwordEncode;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.appUserRoleRepository = appUserRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public String loginUsingUsernameAndPassword(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        if (authentication.isAuthenticated()) {
            List<String> authorities = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).toList();
            return jwtComponent.generateToken(username,authorities);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @Override
    public String registerUserUsingUsernameAndPassword(String username, String password){
        if (appUserRepository.findByUsername(username) != null)
            throw new RuntimeException("Username already exist!");
        AppUserDocument appUserDocument = createAppUserDocumentWithUsernameAndPassword(username, password);
        appUserDocument = appUserRepository.save(appUserDocument);
        AppUserRoleDocument appUserRoleDocument = createAppUserRoleDocumentWithUsername(appUserDocument.getPrivateID());
        appUserRoleRepository.save(appUserRoleDocument);
        return "User created!";
    }

    private AppUserRoleDocument createAppUserRoleDocumentWithUsername(UUID privateID) {
        AppUserRoleDocument appUserRoleDocument = new AppUserRoleDocument();
        appUserRoleDocument.setPrivateId(privateID);
        appUserRoleDocument.setUserRole(List.of("USER"));
        return appUserRoleDocument;
    }

    private AppUserDocument createAppUserDocumentWithUsernameAndPassword(String username, String password) {
     AppUserDocument appUserDocument = new AppUserDocument();
     appUserDocument.setOnline(false);
     appUserDocument.setTopicID(null);
     appUserDocument.setUsername(username);
     appUserDocument.setPublicID(UUID.randomUUID());
     appUserDocument.setPrivateID(UUID.randomUUID());
     appUserDocument.setPassword(passwordEncode.encode(password));
     return appUserDocument;
    }
}
