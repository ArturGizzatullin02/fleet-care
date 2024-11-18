package dev.gizzatullin.repository;

import dev.gizzatullin.model.sparepart.SparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class SparePartRepositoryImpl implements SparePartRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SparePartRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public SparePart save(SparePart sparePart) {
        String sql = "INSERT INTO spare_parts (name, stock_quantity, price) VALUES (:name, :stockQuantity, :price)";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", sparePart.getName())
                .addValue("stockQuantity", sparePart.getStockQuantity())
                .addValue("price", sparePart.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        sparePart.setId(keyHolder.getKeyAs(Long.class));
        return sparePart;
    }

    @Override
    public Optional<SparePart> findById(Long id) {
        String sql = "SELECT * FROM spare_parts WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> SparePart.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .stockQuantity(rs.getInt("stock_quantity"))
                        .price(rs.getDouble("price"))
                        .build())
                .stream()
                .findFirst();
    }

    @Override
    public Collection<SparePart> findAll() {
        String sql = "SELECT * FROM spare_parts";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> SparePart.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .price(rs.getDouble("price"))
                .build());
    }

    @Override
    public SparePart update(SparePart sparePart) {
        String sql = "UPDATE spare_parts SET name = :name, stock_quantity = :stockQuantity, price = :price WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", sparePart.getId())
                .addValue("name", sparePart.getName())
                .addValue("stockQuantity", sparePart.getStockQuantity())
                .addValue("price", sparePart.getPrice());

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, parameters);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Spare part with ID " + sparePart.getId() + " not found.");
        }
        return sparePart;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM spare_parts WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        int rowsDeleted = namedParameterJdbcTemplate.update(sql, parameters);
        if (rowsDeleted == 0) {
            throw new RuntimeException("Spare part with ID " + id + " not found.");
        }
    }
}
