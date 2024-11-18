package dev.gizzatullin.dto;

import dev.gizzatullin.model.vehicle.VehicleStatus;
import dev.gizzatullin.model.vehicle.VehicleTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateRequest {

    @NotNull
    private VehicleTypeName vehicleTypeName; // Только имя типа (enum)

    @NotNull
    private Integer mileage;

    @Builder.Default
    private VehicleStatus vehicleStatus = VehicleStatus.AVAILABLE;

    private String registrationNumber;
}
