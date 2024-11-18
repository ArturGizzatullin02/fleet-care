package dev.gizzatullin.service;

import dev.gizzatullin.model.sparepart.SparePart;

import java.util.Collection;
import java.util.Optional;

public interface SparePartService {

    SparePart saveSparePart(SparePart sparePart);

    Optional<SparePart> getSparePartById(Long id);

    Collection<SparePart> getAllSpareParts();

    SparePart updateSparePart(SparePart sparePart);

    void deleteSparePart(Long id);
}
