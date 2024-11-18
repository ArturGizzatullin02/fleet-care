package dev.gizzatullin.service;

import dev.gizzatullin.exception.EntityNotFoundException;
import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        log.info("Saving vehicle: {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        log.info("Retrieving vehicle by ID: {}", id);
        return vehicleRepository.findById(id);
    }

    @Override
    public Collection<Vehicle> getAllVehicles() {
        log.info("Retrieving all vehicles");
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        log.info("Updating vehicle: {}", vehicle);
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(vehicle.getId());
        if (existingVehicle.isEmpty()) {
            log.warn("Vehicle with ID {} not found", vehicle.getId());
            throw new EntityNotFoundException("Vehicle with ID " + vehicle.getId() + " not found");
        }
        return vehicleRepository.update(vehicle);
    }

    @Override
    public void deleteVehicleById(Long id) {
        log.info("Deleting vehicle by ID: {}", id);
        Optional<Vehicle> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isEmpty()) {
            log.warn("Vehicle with ID {} not found", id);
            throw new EntityNotFoundException("Vehicle with ID " + id + " not found");
        }
        vehicleRepository.deleteById(id);
    }
}
