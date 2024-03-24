package de.ij3rry.chatApp.services;

import de.ij3rry.chatApp.dots.SearchUserDetailsDTO;

public interface AppUserService {

    SearchUserDetailsDTO searchUserWithUsername(String username);
}
