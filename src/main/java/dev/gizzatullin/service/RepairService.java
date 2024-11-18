package dev.gizzatullin.service;

import dev.gizzatullin.model.repair.Repair;

import java.util.List;
import java.util.Optional;

public interface RepairService {

    Repair createRepair(Repair repair);

    Optional<Repair> getRepairById(Long id);

    List<Repair> getAllRepairs();

    Repair updateRepair(Repair repair);

    void deleteRepair(Long id);
}
