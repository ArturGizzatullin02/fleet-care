package dev.gizzatullin.service;

import dev.gizzatullin.model.vehicle.Vehicle;

import java.util.Collection;
import java.util.Optional;

public interface VehicleService {

    Vehicle saveVehicle(Vehicle vehicle);

    Optional<Vehicle> getVehicleById(Long id);

    Collection<Vehicle> getAllVehicles();

    Vehicle updateVehicle(Vehicle vehicle);

    void deleteVehicleById(Long id);
}
