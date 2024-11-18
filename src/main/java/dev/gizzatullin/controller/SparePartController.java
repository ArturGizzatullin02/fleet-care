package dev.gizzatullin.controller;

import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.service.SparePartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/spare-parts")
public class SparePartController {

    private static final Logger logger = LoggerFactory.getLogger(SparePartController.class);

    private final SparePartService sparePartService;

    @Autowired
    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @PostMapping
    public ResponseEntity<SparePart> createSparePart(@RequestBody SparePart sparePart) {
        logger.info("Received request to create spare part: {}", sparePart);
        SparePart savedSparePart = sparePartService.saveSparePart(sparePart);
        logger.info("Spare part created: {}", savedSparePart);
        return ResponseEntity.ok(savedSparePart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePart> getSparePartById(@PathVariable Long id) {
        logger.info("Received request to fetch spare part with ID: {}", id);
        Optional<SparePart> sparePart = sparePartService.getSparePartById(id);
        if (sparePart.isPresent()) {
            logger.info("Returning spare part: {}", sparePart.get());
            return ResponseEntity.ok(sparePart.get());
        } else {
            logger.warn("Spare part with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<SparePart>> getAllSpareParts() {
        logger.info("Received request to fetch all spare parts");
        Collection<SparePart> spareParts = sparePartService.getAllSpareParts();
        logger.info("Returning {} spare parts", spareParts.size());
        return ResponseEntity.ok(spareParts);
    }

    @PutMapping
    public ResponseEntity<SparePart> updateSparePart(@RequestBody SparePart sparePart) {
        logger.info("Received request to update spare part: {}", sparePart);
        SparePart updatedSparePart = sparePartService.updateSparePart(sparePart);
        logger.info("Updated spare part: {}", updatedSparePart);
        return ResponseEntity.ok(updatedSparePart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSparePart(@PathVariable Long id) {
        logger.info("Received request to delete spare part with ID: {}", id);
        sparePartService.deleteSparePart(id);
        logger.info("Spare part with ID: {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
