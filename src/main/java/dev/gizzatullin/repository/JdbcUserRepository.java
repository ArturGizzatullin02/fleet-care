package dev.gizzatullin.repository;

import dev.gizzatullin.mapper.UserRowMapper;
import dev.gizzatullin.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final UserRowMapper mapper;

    @Override
    public User save(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO USERS (first_name, last_name, role) VALUES (:firstName, :lastName, :role)";
        jdbc.update(sql, mapper.toMapSqlParameterSource(user), keyHolder, new String[]{"id"});
        user.setId(keyHolder.getKeyAs(Long.class));
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM USERS WHERE id = :id";
        return jdbc.query(sql, new MapSqlParameterSource("id", id),
                (ResultSet rs) -> {
                    User user = null;
                    while (rs.next()) {
                        if (user == null) {
                            user = mapper.mapRow(rs, rs.getRow());
                        }
                    }
                    return Optional.ofNullable(user);
                });
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return jdbc.query(sql, mapper);
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USERS SET first_name = :firstName, last_name = :lastName, role = :role WHERE id = :id";
        jdbc.update(sql, mapper.toMapSqlParameterSource(user));
        return user;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM USERS WHERE id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", id));
    }
}
