package org.puravidagourmet.admin.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.puravidagourmet.admin.config.AppProperties;
import org.puravidagourmet.admin.config.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

  private final AppProperties appProperties;

  public TokenService(AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  public String createToken(UserPrincipal userPrincipal) {
    return generateToken(userPrincipal, appProperties.getAuth().getTokenExpirationMsec());
  }

  public String createRefreshToken(UserPrincipal userPrincipal) {
    return generateToken(userPrincipal, appProperties.getAuth().getTokenRefreshExpirationMsec());
  }

  public String getUserIdFromToken(String token) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(appProperties.getAuth().getTokenSecret())
            .parseClaimsJws(token)
            .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser()
          .setSigningKey(appProperties.getAuth().getTokenSecret())
          .parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      LOGGER.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty.");
    }
    return false;
  }

  private String generateToken(UserPrincipal userPrincipal, long expiration) {
    return Jwts.builder()
        .setSubject(userPrincipal.getUsername()) // here we can add more info if needed.
        //            .setPayload(userPrincipal.toString()) // here we can add more info if needed.
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
        .compact();
  }
}
