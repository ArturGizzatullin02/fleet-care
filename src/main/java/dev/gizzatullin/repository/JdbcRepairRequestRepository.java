package dev.gizzatullin.repository;

import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.model.request.RequestStatus;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.model.user.UserRole;
import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.model.vehicle.VehicleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcRepairRequestRepository implements RepairRequestRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public RepairRequest save(RepairRequest repairRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO repair_requests (user_id, vehicle_id, comment, request_status)" +
                " VALUES (:userId, :vehicleId, :comment, :requestStatus)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", repairRequest.getId());
        params.addValue("userId", repairRequest.getUser().getId());
        params.addValue("vehicleId", repairRequest.getVehicle().getId());
        params.addValue("comment", repairRequest.getComment());
        params.addValue("requestStatus", repairRequest.getStatus().toString());

        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        repairRequest.setId(keyHolder.getKeyAs(Long.class));
        repairRequest.setUser(findUserById(repairRequest.getUser().getId()));
        repairRequest.setVehicle(findVehicleById(repairRequest.getVehicle().getId()));
        return repairRequest;
    }

    @Override
    public Optional<RepairRequest> findById(Long id) {
        String sql = "SELECT * FROM repair_requests WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return Optional.ofNullable(jdbc.queryForObject(sql, params, new RowMapper<RepairRequest>() {
            @Override
            public RepairRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                RepairRequest repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));

                long userId = rs.getLong("user_id");
                User user = findUserById(userId);
                repairRequest.setUser(user);

                long vehicleId = rs.getLong("vehicle_id");
                Vehicle vehicle = findVehicleById(vehicleId);
                repairRequest.setVehicle(vehicle);

                repairRequest.setComment(rs.getString("comment"));
                repairRequest.setStatus(RequestStatus.valueOf(rs.getString("request_status")));
                return repairRequest;
            }
        }));
    }

    @Override
    public Collection<RepairRequest> findAll() {
        String sql = "SELECT * FROM repair_requests";
        return jdbc.query(sql, new RowMapper<RepairRequest>() {
            @Override
            public RepairRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                RepairRequest repairRequest = new RepairRequest();
                repairRequest.setId(rs.getLong("id"));

                long userId = rs.getLong("user_id");
                User user = findUserById(userId);
                repairRequest.setUser(user);

                long vehicleId = rs.getLong("vehicle_id");
                Vehicle vehicle = findVehicleById(vehicleId);
                repairRequest.setVehicle(vehicle);

                repairRequest.setComment(rs.getString("comment"));
                repairRequest.setStatus(RequestStatus.valueOf(rs.getString("request_status")));
                return repairRequest;
            }
        });
    }

    private User findUserById(long userId) {
        String sql = "SELECT * FROM users WHERE id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        return jdbc.queryForObject(sql, params, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                return user;
            }
        });
    }

    private Vehicle findVehicleById(long vehicleId) {
        String sql = "SELECT * FROM vehicles WHERE id = :vehicleId";
        Map<String, Object> params = new HashMap<>();
        params.put("vehicleId", vehicleId);

        return jdbc.queryForObject(sql, params, new RowMapper<Vehicle>() {
            @Override
            public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getLong("id"));
//                vehicle.setType(n); // TODO добавить findVehicleType
                vehicle.setMileage(rs.getInt("mileage"));
                vehicle.setVehicleStatus(VehicleStatus.valueOf(rs.getString("repair_status")));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                return vehicle;
            }
        });
    }

    @Override
    public RepairRequest update(RepairRequest repairRequest) {
        String sql = "UPDATE repair_requests SET user_id = :userId," +
                " vehicle_id = :vehicleId, comment = :comment, request_status = :requestStatus WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", repairRequest.getId());
        params.addValue("userId", repairRequest.getUser().getId());
        params.addValue("vehicleId", repairRequest.getVehicle().getId());
        params.addValue("comment", repairRequest.getComment());
        params.addValue("requestStatus", repairRequest.getStatus().toString());
        jdbc.update(sql, params);
        repairRequest.setUser(findUserById(repairRequest.getUser().getId()));
        repairRequest.setVehicle(findVehicleById(repairRequest.getVehicle().getId()));
        return repairRequest;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM repair_requests WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update(sql, params);
    }
}

