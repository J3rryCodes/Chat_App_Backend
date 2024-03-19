package de.ij3rry.chatApp.controllers;

import de.ij3rry.chatApp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    String login(String username,String password){
        return authenticationService.loginUsingUsernameAndPassword(username,password);
    }

    @PostMapping("/register")
    String register(String username, String password){
        return authenticationService.registerUserUsingUsernameAndPassword(username,password);
    }
}
