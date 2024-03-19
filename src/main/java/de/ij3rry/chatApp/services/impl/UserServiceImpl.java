package de.ij3rry.chatApp.services.impl;

import de.ij3rry.chatApp.documents.AppUserDocument;
import de.ij3rry.chatApp.documents.AppUserRoleDocument;
import de.ij3rry.chatApp.repositories.AppUserRepository;
import de.ij3rry.chatApp.repositories.AppUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserDetailsService {

    final private AppUserRepository appUserRepository;
    final private AppUserRoleRepository appUserRoleRepository;

    @Autowired
    UserServiceImpl(AppUserRepository appUserRepository, AppUserRoleRepository appUserRoleRepository){
        this.appUserRepository = appUserRepository;
        this.appUserRoleRepository = appUserRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUserDocument appUserDocument = appUserRepository.findByUsername(username);
        if( appUserDocument == null )
            throw new UsernameNotFoundException("User not found!");
        AppUserRoleDocument appUserRoleDocument = appUserRoleRepository.findByPrivateId(appUserDocument.getPrivateID());
        List<SimpleGrantedAuthority> grantedAuthorities = appUserRoleDocument.getUserRole().stream().map(SimpleGrantedAuthority::new).toList();
        return new User(appUserDocument.getUsername(), appUserDocument.getPassword(), grantedAuthorities);
    }
}
