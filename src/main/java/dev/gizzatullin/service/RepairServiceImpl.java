package dev.gizzatullin.service;

import dev.gizzatullin.model.repair.Repair;
import dev.gizzatullin.repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;

    @Override
    public Repair createRepair(Repair repair) {
        log.info("Создание нового ремонта: {}", repair);
        Repair savedRepair = repairRepository.save(repair);
        log.info("Ремонт успешно создан с ID: {}", savedRepair.getId());
        return savedRepair;
    }

    @Override
    public Optional<Repair> getRepairById(Long id) {
        log.info("Поиск ремонта с ID: {}", id);
        Optional<Repair> repair = repairRepository.findById(id);
        if (repair.isPresent()) {
            log.info("Ремонт найден: {}", repair.get());
        } else {
            log.warn("Ремонт с ID {} не найден.", id);
        }
        return repair;
    }

    @Override
    public List<Repair> getAllRepairs() {
        log.info("Запрос на получение всех ремонтов.");
        List<Repair> repairs = repairRepository.findAll();
        log.info("Получено {} ремонтов.", repairs.size());
        return repairs;
    }

    @Override
    public Repair updateRepair(Repair repair) {
        log.info("Обновление ремонта с ID {}: {}", repair.getId(), repair);
        Repair updatedRepair = repairRepository.update(repair);
        log.info("Ремонт с ID {} успешно обновлен.", updatedRepair.getId());
        return updatedRepair;
    }

    @Override
    public void deleteRepair(Long id) {
        log.info("Удаление ремонта с ID: {}", id);
        repairRepository.delete(id);
        log.info("Ремонт с ID {} успешно удалён.", id);
    }
}
