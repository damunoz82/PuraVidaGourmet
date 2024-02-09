package org.puravidatgourmet.security.controllers;

import org.puravidatgourmet.security.config.security.CurrentUser;
import org.puravidatgourmet.security.config.security.UserPrincipal;
import org.puravidatgourmet.security.db.repository.UsuarioRepository;
import org.puravidatgourmet.security.domain.User;
import org.puravidatgourmet.security.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("checking...");
        return userRepository.findByEmail(userPrincipal.getName())
                .orElseThrow(
                		() -> new ResourceNotFoundException("User", "id", userPrincipal.getName()));
    }
}
