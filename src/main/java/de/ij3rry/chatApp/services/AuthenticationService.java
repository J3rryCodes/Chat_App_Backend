package de.ij3rry.chatApp.services;

public interface AuthenticationService {

    String loginUsingUsernameAndPasswordForUser(String username, String password);

    String registerUserUsingUsernameAndPassword(String username, String password);
}
