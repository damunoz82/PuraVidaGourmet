package org.puravidagourmet.admin.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Auth auth = new Auth();
  private final OAuth2 oauth2 = new OAuth2();
  private final RabbitMQ rabbitMQ = new RabbitMQ();

  public Auth getAuth() {
    return auth;
  }

  public OAuth2 getOauth2() {
    return oauth2;
  }

  public RabbitMQ getRabbitMQ() {
    return rabbitMQ;
  }

  public static class RabbitMQ {
    private String exchange;
    private String cocinaQueue;
    private String barQueue;
    private String compraQueue;
    private String cocinaKey;
    private String barKey;
    private String compraKey;

    public String getExchange() {
      return exchange;
    }

    public void setExchange(String exchange) {
      this.exchange = exchange;
    }

    public String getCocinaQueue() {
      return cocinaQueue;
    }

    public void setCocinaQueue(String cocinaQueue) {
      this.cocinaQueue = cocinaQueue;
    }

    public String getBarQueue() {
      return barQueue;
    }

    public void setBarQueue(String barQueue) {
      this.barQueue = barQueue;
    }

    public String getCompraQueue() {
      return compraQueue;
    }

    public void setCompraQueue(String compraQueue) {
      this.compraQueue = compraQueue;
    }

    public String getCocinaKey() {
      return cocinaKey;
    }

    public void setCocinaKey(String cocinaKey) {
      this.cocinaKey = cocinaKey;
    }

    public String getBarKey() {
      return barKey;
    }

    public void setBarKey(String barKey) {
      this.barKey = barKey;
    }

    public String getCompraKey() {
      return compraKey;
    }

    public void setCompraKey(String compraKey) {
      this.compraKey = compraKey;
    }
  }

  public static class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;

    private long tokenRefreshExpirationMsec;

    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
      return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
      this.tokenExpirationMsec = tokenExpirationMsec;
    }

    public long getTokenRefreshExpirationMsec() {
      return tokenRefreshExpirationMsec;
    }

    public void setTokenRefreshExpirationMsec(long tokenRefreshExpirationMsec) {
      this.tokenRefreshExpirationMsec = tokenRefreshExpirationMsec;
    }
  }

  public static final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
      return this;
    }
  }
}
