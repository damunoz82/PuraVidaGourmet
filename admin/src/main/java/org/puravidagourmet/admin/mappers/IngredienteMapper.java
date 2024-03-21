package org.puravidagourmet.admin.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {ProductoMapper.class})
@Component
public interface IngredienteMapper {}
