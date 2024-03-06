package org.puravidagourmet.api.domain.converters;

import org.puravidagourmet.api.domain.enums.UnidadMedidas;

public class UnidadMedidaConverter  {

    public Integer convertToDatabaseColumn(UnidadMedidas unidadMedidas) {
        return unidadMedidas.ordinal();
    }

    public UnidadMedidas convertToEntityAttribute(Integer codigo) {
        return UnidadMedidas.getUnidadMedida(codigo);
    }
}
