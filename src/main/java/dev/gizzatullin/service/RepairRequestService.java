package dev.gizzatullin.service;

import dev.gizzatullin.model.request.RepairRequest;

import java.util.Collection;
import java.util.Optional;

public interface RepairRequestService {

    RepairRequest createRepairRequest(RepairRequest repairRequest);

    Optional<RepairRequest> getRepairRequestById(Long id);

    Collection<RepairRequest> getAllRepairRequests();

    RepairRequest updateRepairRequest(RepairRequest repairRequest);

    void deleteRepairRequest(Long id);
}
