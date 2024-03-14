package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.USU_REC001;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.domain.enums.AuthProvider;
import org.puravidagourmet.api.domain.enums.RoleProvider;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.payload.ApiResponse;
import org.puravidagourmet.api.payload.AuthResponse;
import org.puravidagourmet.api.payload.LoginRequest;
import org.puravidagourmet.api.payload.SignUpRequest;
import org.puravidagourmet.api.services.TokenService;
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

  private final AuthenticationManager authenticationManager;

  private final UsuarioRepository usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenService tokenService;

  public AuthController(
      AuthenticationManager authenticationManager,
      UsuarioRepository usuarioRepository,
      PasswordEncoder passwordEncoder,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    final String token = tokenService.createToken((UserPrincipal) authentication.getPrincipal());
    final String refreshToken =
        tokenService.createRefreshToken((UserPrincipal) authentication.getPrincipal());
    return ResponseEntity.ok(
        AuthResponse.builder()
            .userName(authentication.getName())
            .createdAt(new Date())
            .accessToken(token)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .build());
  }

  @PostMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String email;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    email = tokenService.getUserIdFromToken(refreshToken);
    if (email != null) {
      Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
      if (tokenService.validateToken(refreshToken) && usuario.isEnabled()) {
        String accessToken =
            tokenService.createToken(
                new UserPrincipal(
                    usuario.getEmail(), usuario.getPassword(), null, usuario.isEnabled()));
        AuthResponse authResponse =
            AuthResponse.builder()
                .userName(usuario.getEmail())
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
    if (usuarioRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
      throw new PuraVidaExceptionHandler(USU_REC001);
    }

    // Creating user's account
    Usuario usuario = new Usuario();
    usuario.setName(signUpRequest.getName());
    usuario.setEmail(signUpRequest.getEmail());
    usuario.setPassword(signUpRequest.getPassword());
    usuario.setProvider(AuthProvider.LOCAL);
    usuario.setRoles(
        List.of(
            RoleProvider.ROLE_USER)); // DEFAULT USER ROLE...  OTHER ROLES ARE GRANTED PER REQUEST.

    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    Usuario result = usuarioRepository.save(usuario);

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/user/me")
            .buildAndExpand(result.getEmail())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully"));
  }
}
