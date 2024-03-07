package org.puravidagourmet.api.db.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseRepository<T> {

    protected JdbcTemplate template;

    public BaseRepository(JdbcTemplate template) {
        this.template = template;
    }

    protected long performInsert(String sql, T obj) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(conn -> {

            // Pre-compiling SQL
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            prepareStatement(preparedStatement, obj);

            return preparedStatement;

        }, keyHolder);

        return getKey(keyHolder);
    }


    protected abstract void prepareStatement(PreparedStatement ps, T obj) throws SQLException;

    /**
     * Default getKey from key holder implementation
     * @param keyHolder
     * @return key of the inserted row.
     */
    public long getKey(KeyHolder keyHolder) {
        return (long) keyHolder.getKeys().get("id");
    };

}
