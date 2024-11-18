package dev.gizzatullin.repository;

import dev.gizzatullin.model.sparepart.SparePart;

import java.util.Collection;
import java.util.Optional;

public interface SparePartRepository {

    SparePart save(SparePart sparePart);

    Optional<SparePart> findById(Long id);

    Collection<SparePart> findAll();

    SparePart update(SparePart sparePart);

    void deleteById(Long id);
}
