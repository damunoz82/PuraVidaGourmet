package org.puravidatgourmet.api.config;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.puravidatgourmet.api.config.security.RestAuthenticationEntryPoint;
import org.puravidatgourmet.api.config.security.TokenAuthenticationFilter;
import org.puravidatgourmet.api.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import org.puravidatgourmet.api.config.security.oauth2.OAuth2AuthenticationFailureHandler;
import org.puravidatgourmet.api.config.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.puravidatgourmet.api.services.CustomOAuth2UserService;
import org.puravidatgourmet.api.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @RequiredArgsConstructor
// @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

  @Autowired private CustomUserDetailsService customUserDetailsService;

  @Autowired private CustomOAuth2UserService customOAuth2UserService;

  @Autowired private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @Autowired private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

  /*
    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
    the authorization request. But, since our service is stateless, we can't save it in
    the session. We'll save the request in a Base64 encoded cookie instead.
  */
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  //    @Override
  //    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
  //        throws Exception {
  //      authenticationManagerBuilder
  //          .userDetailsService(customUserDetailsService)
  //          .passwordEncoder(passwordEncoder());
  //    }

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
        .cors(AbstractHttpConfigurer::disable)
        //        .formLogin(AbstractHttpConfigurer::disable)
        //        .httpBasic(AbstractHttpConfigurer::disable)
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
                      "/swagger-ui.html",
                      "/webjars/**",
                      "/v2/**",
                      "/swagger-resources/**")
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider())
        .oauth2Login(
            oauth2Login -> {
              oauth2Login.authorizationEndpoint(
                  entryPoint -> {
                    entryPoint.baseUri("/oauth2/authorize");
                    entryPoint.authorizationRequestRepository(
                        cookieAuthorizationRequestRepository());
                  });
              oauth2Login.redirectionEndpoint(redirect -> redirect.baseUri("/oauth2/callback/*"));
              oauth2Login.userInfoEndpoint(u -> u.userService(customOAuth2UserService));
              oauth2Login.successHandler(oAuth2AuthenticationSuccessHandler);
              oauth2Login.failureHandler(oAuth2AuthenticationFailureHandler);
            })
        .httpBasic(AbstractHttpConfigurer::disable)
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  //    @Override
  //  protected void configure(HttpSecurity http) throws Exception {
  ////    http.cors()
  ////        .and()
  ////        .sessionManagement()
  ////        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  ////        .and()
  ////        .csrf()
  ////        .disable()
  ////        .formLogin()
  ////        .disable()
  ////        .httpBasic()
  ////        .disable()
  //        .exceptionHandling()
  //        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
  //        .and()
  ////        .authorizeRequests()
  ////        .antMatchers(
  ////            "/",
  ////            "/error",
  ////            "/favicon.ico",
  ////            "/**/*.png",
  ////            "/**/*.gif",
  ////            "/**/*.svg",
  ////            "/**/*.jpg",
  ////            "/**/*.html",
  ////            "/**/*.css",
  ////            "/**/*.js")
  ////        .permitAll()
  ////        .antMatchers("/auth/**", "/oauth2/**")
  ////        .permitAll()
  ////        // swagger
  ////        .antMatchers("/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**")
  ////        .permitAll()
  ////        .anyRequest()
  ////        .authenticated()
  ////        .and()
  ////        .oauth2Login()
  ////        .authorizationEndpoint()
  ////        .baseUri("/oauth2/authorize")
  ////        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
  ////        .and()
  ////        .redirectionEndpoint()
  ////        .baseUri("/oauth2/callback/*")
  ////        .and()
  ////        .userInfoEndpoint()
  ////        .userService(customOAuth2UserService)
  ////        .and()
  ////        .successHandler(oAuth2AuthenticationSuccessHandler)
  ////        .failureHandler(oAuth2AuthenticationFailureHandler);
  //
  //    // Add our custom Token based authentication filter
  ////    http.addFilterBefore(tokenAuthenticationFilter(),
  // UsernamePasswordAuthenticationFilter.class);
  //  }
}
