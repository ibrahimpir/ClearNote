package de.clearnote.iboknowsbest.clearnote.service.impl;

import de.clearnote.iboknowsbest.clearnote.repository.UserRepository;
import de.clearnote.iboknowsbest.clearnote.entity.User;
import de.clearnote.iboknowsbest.clearnote.service.NoteService;
import de.clearnote.iboknowsbest.clearnote.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Long findUserIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }
}