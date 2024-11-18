package dev.gizzatullin.controller;

import dev.gizzatullin.model.repair.Repair;
import dev.gizzatullin.service.RepairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repairs")
@RequiredArgsConstructor
@Slf4j
public class RepairController {

    private final RepairService repairService;

    @PostMapping
    public ResponseEntity<Repair> createRepair(@RequestBody Repair repair) {
        log.info("Запрос на создание нового ремонта: {}", repair);
        Repair createdRepair = repairService.createRepair(repair);
        log.info("Ремонт успешно создан: {}", createdRepair);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRepair);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repair> getRepairById(@PathVariable Long id) {
        log.info("Запрос на получение ремонта с ID: {}", id);
        Optional<Repair> repair = repairService.getRepairById(id);
        if (repair.isPresent()) {
            log.info("Ремонт найден: {}", repair.get());
            return ResponseEntity.ok(repair.get());
        } else {
            log.warn("Ремонт с ID {} не найден.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Repair>> getAllRepairs() {
        log.info("Запрос на получение всех ремонтов.");
        List<Repair> repairs = repairService.getAllRepairs();
        log.info("Найдено {} ремонтов.", repairs.size());
        return ResponseEntity.ok(repairs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repair> updateRepair(@PathVariable Long id, @RequestBody Repair repair) {
        log.info("Запрос на обновление ремонта с ID {}: {}", id, repair);
        repair.setId(id);
        Repair updatedRepair = repairService.updateRepair(repair);
        log.info("Ремонт успешно обновлен: {}", updatedRepair);
        return ResponseEntity.ok(updatedRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        log.info("Запрос на удаление ремонта с ID: {}", id);
        repairService.deleteRepair(id);
        log.info("Ремонт с ID {} успешно удалён.", id);
        return ResponseEntity.noContent().build();
    }
}
