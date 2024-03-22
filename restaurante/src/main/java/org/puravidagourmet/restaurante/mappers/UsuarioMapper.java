package org.puravidagourmet.restaurante.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.restaurante.domain.entity.Usuario;
import org.puravidagourmet.restaurante.domain.pojo.UsuarioPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsuarioMapper {

  Usuario toUser(UsuarioPojo user);

  UsuarioPojo toUserPojo(Usuario usuario);
}
