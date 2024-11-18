package dev.gizzatullin.repository;

import dev.gizzatullin.model.vehicle.VehicleType;
import dev.gizzatullin.model.vehicle.VehicleTypeName;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleTypeRepositoryImpl implements VehicleTypeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public VehicleType save(VehicleType vehicleType) {
        String sql = """
            INSERT INTO vehicle_types (name, maintenance_interval)
            VALUES (:name, :maintenanceInterval)
            RETURNING id
        """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", vehicleType.getName().name())
                .addValue("maintenanceInterval", vehicleType.getMaintenanceInterval());

        jdbcTemplate.update(sql, parameters, keyHolder);
        vehicleType.setId(keyHolder.getKey().longValue());
        return vehicleType;
    }

    @Override
    public Optional<VehicleType> findById(Long id) {
        String sql = """
            SELECT * FROM vehicle_types WHERE id = :id
        """;

        return jdbcTemplate.query(sql,
                new MapSqlParameterSource("id", id),
                (ResultSet rs) -> rs.next() ? Optional.of(mapRowToVehicleType(rs)) : Optional.empty());
    }

    @Override
    public Collection<VehicleType> findAll() {
        String sql = """
            SELECT * FROM vehicle_types
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToVehicleType(rs));
    }

    @Override
    public VehicleType update(VehicleType vehicleType) {
        String sql = """
            UPDATE vehicle_types
            SET name = :name, maintenance_interval = :maintenanceInterval
            WHERE id = :id
        """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", vehicleType.getId())
                .addValue("name", vehicleType.getName().name())
                .addValue("maintenanceInterval", vehicleType.getMaintenanceInterval());

        jdbcTemplate.update(sql, parameters);
        return vehicleType;
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
            DELETE FROM vehicle_types WHERE id = :id
        """;

        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private VehicleType mapRowToVehicleType(ResultSet rs) throws java.sql.SQLException {
        return VehicleType.builder()
                .id(rs.getLong("id"))
                .name(VehicleTypeName.valueOf(rs.getString("name")))
                .maintenanceInterval(rs.getInt("maintenance_interval"))
                .build();
    }
}
