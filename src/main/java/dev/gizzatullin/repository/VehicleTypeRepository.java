package dev.gizzatullin.repository;

import dev.gizzatullin.model.vehicle.VehicleType;

import java.util.Collection;
import java.util.Optional;

public interface VehicleTypeRepository {

    VehicleType save(VehicleType vehicleType);

    Optional<VehicleType> findById(Long id);

    Collection<VehicleType> findAll();

    VehicleType update(VehicleType vehicleType);

    void deleteById(Long id);
}
