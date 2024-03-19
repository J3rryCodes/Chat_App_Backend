package de.ij3rry.chatApp.services;

public interface AuthenticationService {

    String loginUsingUsernameAndPassword(String username,String password);

    String registerUserUsingUsernameAndPassword(String username, String password);
}
