package de.clearnote.iboknowsbest.clearnote.service;

import de.clearnote.iboknowsbest.clearnote.entity.User;

public interface UserService {
    Long findUserIdByUsername(String username);
    User getUserById(Long userId);
}
