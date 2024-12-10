package dev.gizzatullin.controller;

import dev.gizzatullin.service.RepairRequestService;
import dev.gizzatullin.service.RepairService;
import dev.gizzatullin.service.SparePartService;
import dev.gizzatullin.service.UserService;
import dev.gizzatullin.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delete")
@RequiredArgsConstructor
public class DeleteController {

    private final VehicleService vehicleService;
    private final UserService userService;
    private final RepairRequestService repairRequestService;
    private final SparePartService sparePartService;
    private final RepairService repairService;

    @GetMapping("/entity")
    public String showDeleteForm() {
        return "delete-entity"; // Убедитесь, что delete-entity.html находится в папке templates
    }

    @DeleteMapping("/entity")
    public String deleteEntity(@RequestParam String entityType, @RequestParam Long entityId) {
        // Обработка удаления сущности на основе типа
        switch (entityType) {
            case "vehicle":
                // Логика удаления автомобиля
                vehicleService.deleteVehicleById(entityId);
                break;
            case "repair":
                // Логика удаления ремонта
                repairService.deleteRepair(entityId);
                break;
            case "sparePart":
                // Логика удаления запчасти
                sparePartService.deleteSparePart(entityId);
                break;
            case "user":
                // Логика удаления пользователя
                userService.delete(entityId);
                break;
            case "repairRequest":
                // Логика удаления запроса на ремонт
                repairRequestService.deleteRepairRequest(entityId);
                break;
            default:
                throw new IllegalArgumentException("Некорректный тип сущности: " + entityType);
        }

        // Перенаправление на главную страницу
        return "redirect:/";
    }
}
