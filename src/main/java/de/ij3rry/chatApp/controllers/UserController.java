package de.ij3rry.chatApp.controllers;

import de.ij3rry.chatApp.dots.SearchUserDetailsDTO;
import de.ij3rry.chatApp.services.AppUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/search")
    public SearchUserDetailsDTO searchUserWithUsername(String username){
        return appUserService.searchUserWithUsername(username);
    }
}
