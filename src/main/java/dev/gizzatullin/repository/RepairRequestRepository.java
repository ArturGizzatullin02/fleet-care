package dev.gizzatullin.repository;

import dev.gizzatullin.model.request.RepairRequest;

import java.util.Collection;
import java.util.Optional;

public interface RepairRequestRepository {

    RepairRequest save(RepairRequest repairRequest);

    Optional<RepairRequest> findById(Long id);

    Collection<RepairRequest> findAll();

    RepairRequest update(RepairRequest repairRequest);

    void delete(Long id);
}
