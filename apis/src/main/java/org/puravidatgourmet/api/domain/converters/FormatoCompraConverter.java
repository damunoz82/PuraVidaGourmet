package org.puravidatgourmet.api.domain.converters;

import org.puravidatgourmet.api.domain.enums.FormatoCompra;

public class FormatoCompraConverter {

    public Integer convertToDatabaseColumn(FormatoCompra formatoCompra) {
        return formatoCompra.ordinal();
    }

    public FormatoCompra convertToEntityAttribute(Integer codigo) {
        return FormatoCompra.getFormatoCompra(codigo);
    }
}
