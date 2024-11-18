package dev.gizzatullin.repository;

import dev.gizzatullin.model.repair.Repair;
import dev.gizzatullin.model.repair.RepairStatus;
import dev.gizzatullin.model.repair.RepairType;
import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.model.request.RequestStatus;
import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.model.vehicle.VehicleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RepairRepositoryImpl implements RepairRepository {

    private final NamedParameterJdbcOperations jdbc;

    public Repair save(Repair repair) {
        String sql = "INSERT INTO repairs (vehicle_id, start_date, end_date, master_id, request_id, repair_type, repair_status, repair_cost) " +
                "VALUES (:vehicleId, :startDate, :endDate, :masterId, :requestId, :repairType, :repairStatus, :repairCost)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("vehicleId", repair.getVehicle().getId());
        params.addValue("startDate", repair.getStartDate());
        params.addValue("endDate", repair.getEndDate());
        params.addValue("masterId", repair.getMaster().getId());
        params.addValue("requestId", repair.getRequest().getId());
        params.addValue("repairType", repair.getType().toString());
        params.addValue("repairStatus", repair.getStatus().toString());
        params.addValue("repairCost", repair.getCost());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        repair.setId(keyHolder.getKeyAs(Long.class));

        // Вставка записей в таблицу repair_spare_parts
        if (repair.getSpareParts() != null) {
            String insertSparePartSql = "INSERT INTO repair_spare_parts (repair_id, spare_part_id, quantity) VALUES (:repairId, :sparePartId, :quantity)";

            for (SparePart sparePart : repair.getSpareParts()) {
                MapSqlParameterSource sparePartParams = new MapSqlParameterSource();
                sparePartParams.addValue("repairId", repair.getId());
                sparePartParams.addValue("sparePartId", sparePart.getId());
                sparePartParams.addValue("quantity", sparePart.getStockQuantity()); // Передаем количество

                jdbc.update(insertSparePartSql, sparePartParams);
            }
        }

        return repair;
    }

    public Optional<Repair> findById(Long id) {
        String sql = "SELECT * FROM repairs WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

        return jdbc.query(sql, params, (rs, rowNum) -> mapRowToRepair(rs))
                .stream()
                .findFirst();
    }

    public List<Repair> findAll() {
        String sql = "SELECT * FROM repairs";
        return jdbc.query(sql, (rs, rowNum) -> mapRowToRepair(rs));
    }

    public Repair update(Repair repair) {
        String sql = """
                UPDATE repairs SET vehicle_id = :vehicleId, start_date = :startDate, end_date = :endDate, master_id = :masterId,
                request_id = :requestId, repair_type = :repairType, repair_status = :repairStatus, repair_cost = :repairCost
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", repair.getId())
                .addValue("vehicleId", repair.getVehicle().getId())
                .addValue("startDate", repair.getStartDate())
                .addValue("endDate", repair.getEndDate())
                .addValue("masterId", repair.getMaster().getId())
                .addValue("requestId", repair.getRequest().getId())
                .addValue("repairType", repair.getType().name())
                .addValue("repairStatus", repair.getStatus().name())
                .addValue("repairCost", repair.getCost());

        jdbc.update(sql, params);
        deleteRepairSpareParts(repair.getId());
        saveRepairSpareParts(repair);
        return repair;
    }

    public void delete(Long id) {
        deleteRepairSpareParts(id);

        String sql = "DELETE FROM repairs WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update(sql, params);
    }

    private void saveRepairSpareParts(Repair repair) {
        if (repair.getSpareParts() == null || repair.getSpareParts().isEmpty()) return;

        String sql = "INSERT INTO repair_spare_parts (repair_id, spare_part_id, quantity) VALUES (:repairId, :sparePartId, :quantity)";
        for (SparePart part : repair.getSpareParts()) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("repairId", repair.getId())
                    .addValue("sparePartId", part.getId())
                    .addValue("quantity", part.getStockQuantity());
            jdbc.update(sql, params);
        }
    }

    private void deleteRepairSpareParts(Long repairId) {
        String sql = "DELETE FROM repair_spare_parts WHERE repair_id = :repairId";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("repairId", repairId);
        jdbc.update(sql, params);
    }

    private Repair mapRowToRepair(ResultSet rs) throws SQLException {
        Repair repair = Repair.builder()
                .id(rs.getLong("id"))
                .vehicle(findVehicleById(rs.getLong("vehicle_id")))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null)
                .master(findUserById(rs.getLong("master_id")))
                .request(findRequestById(rs.getLong("request_id")))
                .type(RepairType.valueOf(rs.getString("repair_type")))
                .status(RepairStatus.valueOf(rs.getString("repair_status")))
                .cost(rs.getDouble("repair_cost"))
                .spareParts(findSparePartsByRepairId(rs.getLong("id")))
                .build();
        return repair;
    }

    private Vehicle findVehicleById(Long vehicleId) {
        String sql = "SELECT * FROM vehicles WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", vehicleId);
        return jdbc.queryForObject(sql, params, this::mapRowToVehicle);
    }

    private RepairRequest findRequestById(Long requestId) {
        String sql = "SELECT * FROM repair_requests WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", requestId);
        return jdbc.queryForObject(sql, params, this::mapRowToRepairRequest);
    }

    private User findUserById(Long userId) {
        String sql = "SELECT * FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", userId);
        return jdbc.queryForObject(sql, params, this::mapRowToUser);
    }

    private Set<SparePart> findSparePartsByRepairId(Long repairId) {
        String sql = """
                SELECT sp.* FROM spare_parts sp
                JOIN repair_spare_parts rsp ON sp.id = rsp.spare_part_id
                WHERE rsp.repair_id = :repairId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("repairId", repairId);
        return new HashSet<>(jdbc.query(sql, params, this::mapRowToSparePart));
    }

    private Vehicle mapRowToVehicle(ResultSet rs, int rowNum) throws SQLException {
        return Vehicle.builder()
                .id(rs.getLong("id"))
                .licensePlate(rs.getString("license_plate"))
                .mileage(rs.getInt("mileage"))
                .vehicleStatus(VehicleStatus.valueOf(rs.getString("repair_status")))
                .build();
    }

    private RepairRequest mapRowToRepairRequest(ResultSet rs, int rowNum) throws SQLException {
        return RepairRequest.builder()
                .id(rs.getLong("id"))
                .comment(rs.getString("comment"))
                .status(RequestStatus.valueOf(rs.getString("request_status")))
                .build();
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }

    private SparePart mapRowToSparePart(ResultSet rs, int rowNum) throws SQLException {
        return SparePart.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getDouble("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .build();
    }
}
