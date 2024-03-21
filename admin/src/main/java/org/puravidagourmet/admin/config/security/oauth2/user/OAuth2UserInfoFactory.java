package org.puravidagourmet.admin.config.security.oauth2.user;

import java.util.Map;
import org.puravidagourmet.admin.domain.enums.AuthProvider;
import org.puravidagourmet.admin.exceptions.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo getOAuth2UserInfo(
      String registrationId, Map<String, Object> attributes) {
    if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
      return new GoogleOAuth2UserInfo(attributes);

      //		} else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
      //			return new FacebookOAuth2UserInfo(attributes);

    } else {
      throw new OAuth2AuthenticationProcessingException(
          "Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
