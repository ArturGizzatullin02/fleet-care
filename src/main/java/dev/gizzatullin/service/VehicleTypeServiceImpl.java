package dev.gizzatullin.service;

import dev.gizzatullin.model.vehicle.VehicleType;
import dev.gizzatullin.repository.VehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository;

    @Override
    public VehicleType saveVehicleType(VehicleType vehicleType) {
        log.info("Saving new vehicle type: {}", vehicleType);
        VehicleType saved = vehicleTypeRepository.save(vehicleType);
        log.info("Saved vehicle type with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public Optional<VehicleType> getVehicleTypeById(Long id) {
        log.info("Fetching vehicle type by ID: {}", id);
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(id);
        if (vehicleType.isPresent()) {
            log.info("Found vehicle type: {}", vehicleType.get());
        } else {
            log.warn("Vehicle type with ID {} not found", id);
        }
        return vehicleType;
    }

    @Override
    public Collection<VehicleType> getAllVehicleTypes() {
        log.info("Fetching all vehicle types");
        Collection<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        log.info("Found {} vehicle types", vehicleTypes.size());
        return vehicleTypes;
    }

    @Override
    public VehicleType updateVehicleType(VehicleType vehicleType) {
        log.info("Updating vehicle type with ID: {}", vehicleType.getId());
        VehicleType updated = vehicleTypeRepository.update(vehicleType);
        log.info("Updated vehicle type: {}", updated);
        return updated;
    }

    @Override
    public void deleteVehicleTypeById(Long id) {
        log.info("Deleting vehicle type with ID: {}", id);
        vehicleTypeRepository.deleteById(id);
        log.info("Deleted vehicle type with ID: {}", id);
    }
}
