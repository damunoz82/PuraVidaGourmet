package org.puravidatgourmet.api.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.puravidatgourmet.api.config.security.UserPrincipal;
import org.puravidatgourmet.api.db.repository.UsuarioRepository;
import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.puravidatgourmet.api.payload.ApiResponse;
import org.puravidatgourmet.api.payload.AuthResponse;
import org.puravidatgourmet.api.payload.LoginRequest;
import org.puravidatgourmet.api.payload.SignUpRequest;
import org.puravidatgourmet.api.services.TokenService;
import org.puravidatgourmet.api.utils.AuthProvider;
import org.puravidatgourmet.api.utils.RoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = tokenService.createToken((UserPrincipal) authentication.getPrincipal());
        final String refreshToken = tokenService.createRefreshToken((UserPrincipal) authentication.getPrincipal());
        return ResponseEntity.ok(AuthResponse.builder()
                        .userName(authentication.getName())
                        .createdAt(new Date())
                .accessToken(token)
                .refreshToken(refreshToken)
                        .tokenType("Bearer")
                .build());
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = tokenService.getUserIdFromToken(refreshToken);
        if (email != null) {
            User user = userRepository.findByEmail(email).orElseThrow();
            if (tokenService.validateToken(refreshToken) && user.isEnabled()) {
                String accessToken = tokenService.createToken(new UserPrincipal(user.getEmail(), user.getPassword(), null, user.isEnabled()));
                AuthResponse authResponse = AuthResponse.builder()
                        .userName(user.getEmail())
                        .createdAt(new Date())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .tokenType("Bearer")
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.LOCAL);
        user.setRoles(List.of(RoleProvider.ROLE_USER));   // DEFAULT USER ROLE...  OTHER ROLES ARE GRANTED PER REQUEST.

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }
}
