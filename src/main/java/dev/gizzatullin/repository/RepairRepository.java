package dev.gizzatullin.repository;

import dev.gizzatullin.model.repair.Repair;

import java.util.List;
import java.util.Optional;

public interface RepairRepository {

    Repair save(Repair repair);

    Optional<Repair> findById(Long id);

    List<Repair> findAll();

    Repair update(Repair repair);

    void delete(Long id);
}
