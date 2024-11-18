package dev.gizzatullin.repository;

import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.model.vehicle.VehicleStatus;
import dev.gizzatullin.model.vehicle.VehicleType;
import dev.gizzatullin.model.vehicle.VehicleTypeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public VehicleRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        String sql = """
                    INSERT INTO vehicles (vehicle_type_id, mileage, repair_status, license_plate)
                    VALUES (:vehicleTypeId, :mileage, :repairStatus, :licensePlate)
                    RETURNING id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("vehicleTypeId", vehicle.getType().getId())
                .addValue("mileage", vehicle.getMileage())
                .addValue("repairStatus", vehicle.getVehicleStatus().name())
                .addValue("licensePlate", vehicle.getLicensePlate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(sql, params, keyHolder);

        vehicle.setId(keyHolder.getKey().longValue());
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        String sql = """
                SELECT v.id, v.mileage, v.repair_status,
                       vt.id AS type_id, vt.name AS type_name, vt.maintenance_interval, v.license_plate
                FROM vehicles v
                JOIN vehicle_types vt ON v.vehicle_type_id = vt.id
                WHERE v.id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.query(sql, params, this::mapRow).stream().findFirst();
    }

    @Override
    public Collection<Vehicle> findAll() {
        String sql = """
                SELECT v.id, v.mileage, v.repair_status,
                       vt.id AS type_id, vt.name AS type_name, vt.maintenance_interval, v.license_plate
                FROM vehicles v
                JOIN vehicle_types vt ON v.vehicle_type_id = vt.id
                """;
        return jdbcOperations.query(sql, this::mapRow);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        String sql = """
                    UPDATE vehicles
                    SET vehicle_type_id = :vehicleTypeId,
                        mileage = :mileage,
                        repair_status = :repairStatus,
                        license_plate = :licensePlate
                    WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("vehicleTypeId", vehicle.getType().getId())
                .addValue("mileage", vehicle.getMileage())
                .addValue("repairStatus", vehicle.getVehicleStatus().name())
                .addValue("licensePlate", vehicle.getLicensePlate())
                .addValue("id", vehicle.getId());

        int rowsUpdated = jdbcOperations.update(sql, params);
        if (rowsUpdated == 0) {
            throw new IllegalArgumentException("Vehicle with ID " + vehicle.getId() + " not found");
        }
        return vehicle;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM vehicles WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbcOperations.update(sql, params);
    }

    private Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Vehicle.builder()
                .id(rs.getLong("id"))
                .mileage(rs.getInt("mileage"))
                .vehicleStatus(VehicleStatus.valueOf(rs.getString("repair_status")))
                .type(VehicleType.builder()
                        .id(rs.getLong("type_id"))
                        .name(VehicleTypeName.valueOf(rs.getString("type_name")))
                        .maintenanceInterval(rs.getInt("maintenance_interval"))
                        .build())
                .licensePlate(rs.getString("license_plate"))
                .build();
    }
}
