package org.puravidagourmet.api.config.security.oauth2;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.AUTH_REC001;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import org.puravidagourmet.api.config.AppProperties;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.services.TokenService;
import org.puravidagourmet.api.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final TokenService tokenService;

  private final AppProperties appProperties;

  private final HttpCookieOAuth2AuthorizationRequestRepository
      httpCookieOAuth2AuthorizationRequestRepository;

  @Autowired
  OAuth2AuthenticationSuccessHandler(
      TokenService tokenService,
      AppProperties appProperties,
      HttpCookieOAuth2AuthorizationRequestRepository
          httpCookieOAuth2AuthorizationRequestRepository) {
    this.tokenService = tokenService;
    this.appProperties = appProperties;
    this.httpCookieOAuth2AuthorizationRequestRepository =
        httpCookieOAuth2AuthorizationRequestRepository;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    String targetUrl = determineTargetUrl(request, response, authentication);

    if (response.isCommitted()) {
      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    Optional<String> redirectUri =
        CookieUtils.getCookie(
                request,
                HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue);

    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
      throw new PuraVidaExceptionHandler(AUTH_REC001);
    }

    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

    String token = tokenService.createToken((UserPrincipal) authentication.getPrincipal());

    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token", token)
        .build()
        .toUriString();
  }

  protected void clearAuthenticationAttributes(
      HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
        request, response);
  }

  private boolean isAuthorizedRedirectUri(String uri) {
    URI clientRedirectUri = URI.create(uri);

    return appProperties.getOauth2().getAuthorizedRedirectUris().stream()
        .anyMatch(
            authorizedRedirectUri -> {
              // Only validate host and port. Let the clients use different paths if they want to
              URI authorizedURI = URI.create(authorizedRedirectUri);
              return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                  && authorizedURI.getPort() == clientRedirectUri.getPort();
            });
  }
}
