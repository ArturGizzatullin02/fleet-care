package dev.gizzatullin.service;

import dev.gizzatullin.exception.EntityNotFoundException;
import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.repository.RepairRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairRequestServiceImpl implements RepairRequestService {

    private final RepairRequestRepository repairRequestRepository;

    @Override
    public RepairRequest createRepairRequest(RepairRequest repairRequest) {
        return repairRequestRepository.save(repairRequest);
    }

    @Override
    public Optional<RepairRequest> getRepairRequestById(Long id) {
        return repairRequestRepository.findById(id);
    }

    @Override
    public Collection<RepairRequest> getAllRepairRequests() {
        return repairRequestRepository.findAll();
    }

    @Override
    public RepairRequest updateRepairRequest(RepairRequest repairRequest) {
        return repairRequestRepository.update(repairRequest);
    }

    @Override
    public void deleteRepairRequest(Long id) {
        repairRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("RepairRequest with id %s not found", id)));
        repairRequestRepository.delete(id);
    }
}
