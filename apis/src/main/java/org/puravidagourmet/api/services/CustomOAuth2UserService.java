package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.config.security.oauth2.user.OAuth2UserInfo;
import org.puravidagourmet.api.config.security.oauth2.user.OAuth2UserInfoFactory;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.domain.enums.AuthProvider;
import org.puravidagourmet.api.domain.enums.RoleProvider;
import org.puravidagourmet.api.exceptions.OAuth2AuthenticationProcessingException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UsuarioRepository usuarioRepository;

  public CustomOAuth2UserService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the
      // OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo =
        OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(),
            oAuth2User.getAttributes());
    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    Optional<Usuario> userOptional = usuarioRepository.findByEmail(oAuth2UserInfo.getEmail());
    Usuario usuario;
    if (userOptional.isPresent()) {
      usuario = userOptional.get();
      if (!usuario
          .getProvider()
          .equals(
              AuthProvider.valueOf(
                  oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new OAuth2AuthenticationProcessingException(
            "Looks like you're signed up with "
                + usuario.getProvider()
                + " account. Please use your "
                + usuario.getProvider()
                + " account to login.");
      }
      usuario = updateExistingUser(usuario, oAuth2UserInfo);
    } else {
      usuario = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserPrincipal.create(usuario, oAuth2User.getAttributes());
  }

  private Usuario registerNewUser(
      OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    Usuario usuario = new Usuario();

    usuario.setProvider(
        AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
    usuario.setProviderId(oAuth2UserInfo.getId());
    usuario.setName(oAuth2UserInfo.getName());
    usuario.setEmail(oAuth2UserInfo.getEmail());
    //        user.setImageUrl(oAuth2UserInfo.getImageUrl());
    usuario.setRoles(
        List.of(
            RoleProvider.ROLE_USER)); // DEFAULT USER ROLE...  OTHER ROLES ARE GRANTED PER REQUEST.
    return usuarioRepository.save(usuario);
  }

  private Usuario updateExistingUser(Usuario existingUsuario, OAuth2UserInfo oAuth2UserInfo) {
    existingUsuario.setName(oAuth2UserInfo.getName());
    //        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
    return usuarioRepository.save(existingUsuario);
  }
}
