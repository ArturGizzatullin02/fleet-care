package dev.gizzatullin.model.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType {
    private Long id;
    private VehicleTypeName name;
    private Integer maintenanceInterval;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleType that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
