package dev.gizzatullin.model.repair;

import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Repair {

    private Long id;

    private Vehicle vehicle;

    private LocalDate startDate;

    private LocalDate endDate;

    private User master;

    private RepairRequest request;

    private RepairType type;

    private RepairStatus status;

    private Double cost;

    private Set<SparePart> spareParts;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repair repair)) return false;

        return Objects.equals(id, repair.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
