package dev.gizzatullin.service;

import dev.gizzatullin.exception.EntityNotFoundException;
import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.repository.SparePartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SparePartServiceImpl implements SparePartService {

    private static final Logger logger = LoggerFactory.getLogger(SparePartServiceImpl.class);

    private final SparePartRepository sparePartRepository;

    @Autowired
    public SparePartServiceImpl(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public SparePart saveSparePart(SparePart sparePart) {
        logger.info("Saving spare part: {}", sparePart);
        SparePart savedSparePart = sparePartRepository.save(sparePart);
        logger.info("Saved spare part with ID: {}", savedSparePart.getId());
        return savedSparePart;
    }

    @Override
    public Optional<SparePart> getSparePartById(Long id) {
        logger.info("Fetching spare part by ID: {}", id);
        Optional<SparePart> sparePart = sparePartRepository.findById(id);
        if (sparePart.isEmpty()) {
            logger.warn("Spare part with ID: {} not found", id);
        } else {
            logger.info("Found spare part: {}", sparePart.get());
        }
        return sparePart;
    }

    @Override
    public Collection<SparePart> getAllSpareParts() {
        logger.info("Fetching all spare parts");
        Collection<SparePart> spareParts = sparePartRepository.findAll();
        logger.info("Fetched {} spare parts", spareParts.size());
        return spareParts;
    }

    @Override
    public SparePart updateSparePart(SparePart sparePart) {
        logger.info("Updating spare part: {}", sparePart);
        // Проверяем наличие перед обновлением
        sparePartRepository.findById(sparePart.getId())
                .orElseThrow(() -> {
                    logger.warn("Spare part with ID: {} not found for update", sparePart.getId());
                    return new EntityNotFoundException("Spare part with ID " + sparePart.getId() + " not found");
                });

        SparePart updatedSparePart = sparePartRepository.update(sparePart);
        logger.info("Updated spare part: {}", updatedSparePart);
        return updatedSparePart;
    }

    @Override
    public void deleteSparePart(Long id) {
        logger.info("Deleting spare part with ID: {}", id);
        sparePartRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Spare part with ID: {} not found for deletion", id);
                    return new EntityNotFoundException("Spare part with ID " + id + " not found");
                });

        sparePartRepository.deleteById(id);
        logger.info("Deleted spare part with ID: {}", id);
    }
}
