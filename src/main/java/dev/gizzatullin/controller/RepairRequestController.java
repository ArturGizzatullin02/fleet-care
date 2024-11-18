package dev.gizzatullin.controller;

import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.service.RepairRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/repair-requests")
@RequiredArgsConstructor
public class RepairRequestController {

    private final RepairRequestService repairRequestService;

    @PostMapping
    public ResponseEntity<RepairRequest> createRepairRequest(@RequestBody RepairRequest repairRequest) {
        RepairRequest createdRequest = repairRequestService.createRepairRequest(repairRequest);
        return ResponseEntity.ok(createdRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairRequest> getRepairRequestById(@PathVariable Long id) {
        return repairRequestService.getRepairRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<RepairRequest>> getAllRepairRequests() {
        Collection<RepairRequest> repairRequests = repairRequestService.getAllRepairRequests();
        return ResponseEntity.ok(repairRequests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairRequest> updateRepairRequest(
            @PathVariable Long id,
            @RequestBody RepairRequest repairRequest) {
        repairRequest.setId(id);
        RepairRequest updatedRequest = repairRequestService.updateRepairRequest(repairRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepairRequest(@PathVariable Long id) {
        repairRequestService.deleteRepairRequest(id);
        return ResponseEntity.noContent().build();
    }
}
