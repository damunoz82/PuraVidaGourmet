package org.puravidatgourmet.api.db.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.puravidatgourmet.api.domain.entity.Ingrediente;
import org.puravidatgourmet.api.domain.entity.Producto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredienteRepository extends BaseRepository<Ingrediente>{

    private final String FIND_BY_RECETA_ID = "select i.ingrediente_id , i.cantidad , i.producto_id, p.nombre  from ingrediente i \n" +
            "join producto p on i.producto_id = p.id where i.receta_id = ?";
    private final String CREATE_INGREDIENTE = "insert into ingrediente (cantidad, producto_id, receta_id) values" +
            "(?, ?, ?)";

    private final String DELETE_FROM_RECETA = "delete from ingrediente where receta_id = ?";

    public IngredienteRepository(JdbcTemplate template) {
        super(template);
    }

    private final RowMapper<Ingrediente> rowMapper = (rs, rowNum) -> {
        Producto producto = Producto.builder()
                .id(rs.getLong("producto_id"))
                .nombre(rs.getString("nombre")).build();
        return Ingrediente.builder()
                .ingredienteId(rs.getLong("ingrediente_id"))
                .cantidad(rs.getLong("cantidad"))
                .producto(producto)
                .build();
    };

    @Override
    public void prepareStatement(PreparedStatement ps, Ingrediente obj) throws SQLException {
        throw new NotImplementedException();
    }

    public void deleteForReceta(long id) {
        template.update(DELETE_FROM_RECETA, id);
    }

    public void save(List<Ingrediente> ingrediente, long receta_id) {
        template.batchUpdate(CREATE_INGREDIENTE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ingrediente.get(i).getCantidad());
                ps.setLong(2, ingrediente.get(i).getProducto().getId());
                ps.setLong(3, receta_id);
            }

            @Override
            public int getBatchSize() {
                return ingrediente.size();
            }
        });
//        template.update(CREATE_INGREDIENTE, ingrediente.getCantidad(), ingrediente.getProducto().getId(), receta_id);
    }

    public List<Ingrediente> findIngredientes(long receta_id) {
        return template.query(FIND_BY_RECETA_ID, rowMapper, receta_id);
    }
}
