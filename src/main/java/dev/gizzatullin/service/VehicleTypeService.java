package dev.gizzatullin.service;

import dev.gizzatullin.model.vehicle.VehicleType;

import java.util.Collection;
import java.util.Optional;

public interface VehicleTypeService {

    VehicleType saveVehicleType(VehicleType vehicleType);

    Optional<VehicleType> getVehicleTypeById(Long id);

    Collection<VehicleType> getAllVehicleTypes();

    VehicleType updateVehicleType(VehicleType vehicleType);

    void deleteVehicleTypeById(Long id);
}
