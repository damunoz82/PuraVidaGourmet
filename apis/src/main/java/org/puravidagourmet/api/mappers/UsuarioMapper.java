package org.puravidagourmet.api.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.domain.pojo.UsuarioPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsuarioMapper {

  Usuario toUser(UsuarioPojo user);

  UsuarioPojo toUserPojo(Usuario usuario);
}
