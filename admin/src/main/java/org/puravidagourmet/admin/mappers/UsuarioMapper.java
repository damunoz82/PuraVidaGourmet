package org.puravidagourmet.admin.mappers;

import org.mapstruct.Mapper;
import org.puravidagourmet.admin.domain.entity.Usuario;
import org.puravidagourmet.admin.domain.pojo.UsuarioPojo;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsuarioMapper {

  Usuario toUser(UsuarioPojo user);

  UsuarioPojo toUserPojo(Usuario usuario);
}
