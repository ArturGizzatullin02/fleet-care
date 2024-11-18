package dev.gizzatullin.model.sparepart;

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
public class SparePart {

    private Long id;

    private String name;

    private Integer stockQuantity;

    private Double price;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SparePart sparePart)) return false;

        return Objects.equals(id, sparePart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
