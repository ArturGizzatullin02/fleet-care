package dev.gizzatullin.controller;

import dev.gizzatullin.model.vehicle.VehicleType;
import dev.gizzatullin.service.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/vehicle-types")
@RequiredArgsConstructor
@Slf4j
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    @PostMapping
    public ResponseEntity<VehicleType> createVehicleType(@RequestBody VehicleType vehicleType) {
        log.info("Received request to create vehicle type: {}", vehicleType);
        VehicleType savedVehicleType = vehicleTypeService.saveVehicleType(vehicleType);
        return ResponseEntity.ok(savedVehicleType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleType> getVehicleTypeById(@PathVariable Long id) {
        log.info("Received request to get vehicle type by ID: {}", id);
        return vehicleTypeService.getVehicleTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<VehicleType>> getAllVehicleTypes() {
        log.info("Received request to get all vehicle types");
        Collection<VehicleType> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
        return ResponseEntity.ok(vehicleTypes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleType> updateVehicleType(@PathVariable Long id, @RequestBody VehicleType vehicleType) {
        log.info("Received request to update vehicle type with ID {}: {}", id, vehicleType);
        vehicleType.setId(id);
        VehicleType updatedVehicleType = vehicleTypeService.updateVehicleType(vehicleType);
        return ResponseEntity.ok(updatedVehicleType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        log.info("Received request to delete vehicle type with ID: {}", id);
        vehicleTypeService.deleteVehicleTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
