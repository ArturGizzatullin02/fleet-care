package dev.gizzatullin.repository;

import dev.gizzatullin.model.vehicle.Vehicle;

import java.util.Collection;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    Collection<Vehicle> findAll();

    Vehicle update(Vehicle vehicle);

    void deleteById(Long id);
}
