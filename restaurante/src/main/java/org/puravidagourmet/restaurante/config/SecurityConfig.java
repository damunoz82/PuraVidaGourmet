package org.puravidagourmet.restaurante.config;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;
import org.puravidagourmet.restaurante.config.security.RestAuthenticationEntryPoint;
import org.puravidagourmet.restaurante.config.security.TokenAuthenticationFilter;
import org.puravidagourmet.restaurante.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// @RequiredArgsConstructor
// @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

  @Autowired private CustomUserDetailsService customUserDetailsService;

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(customUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .cors(
            cors ->
                cors.configurationSource(
                    request -> {
                      CorsConfiguration config = new CorsConfiguration();
                      config.setAllowedOriginPatterns(Arrays.asList("*"));
                      config.setAllowedMethods(
                          Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                      config.setAllowedHeaders(Arrays.asList("*"));
                      config.addExposedHeader("Location");
                      config.setAllowCredentials(true);
                      config.setMaxAge(3600L);
                      return config;
                    }))
        //        .cors(AbstractHttpConfigurer::disable)
        .exceptionHandling(e -> e.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
        .authorizeHttpRequests(
            request -> {
              request
                  .dispatcherTypeMatchers(FORWARD, ERROR)
                  .permitAll()
                  .requestMatchers(
                      "/",
                      "/error",
                      "/favicon.ico",
                      "/*/*.png",
                      "/*/*.gif",
                      "/*/*.svg",
                      "/*/*.jpg",
                      "/*/*.html",
                      "/*/*.css",
                      "/*/*.js",
                      "/auth/**",
                      "/oauth2/**",
                      "/actuator/health",
                      "/swagger-ui.html",
                      "/ordenes-socket",
                      "/ordenes-socket/**",
                      "/webjars/**",
                      "/v3/**",
                      "/swagger-resources/**")
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider())
        .httpBasic(AbstractHttpConfigurer::disable)
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
