package dev.gizzatullin.controller;

import dev.gizzatullin.model.repair.Repair;
import dev.gizzatullin.model.repair.RepairStatus;
import dev.gizzatullin.model.repair.RepairType;
import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.model.request.RequestStatus;
import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.model.user.UserRole;
import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.model.vehicle.VehicleStatus;
import dev.gizzatullin.model.vehicle.VehicleType;
import dev.gizzatullin.model.vehicle.VehicleTypeName;
import dev.gizzatullin.service.RepairRequestService;
import dev.gizzatullin.service.RepairService;
import dev.gizzatullin.service.SparePartService;
import dev.gizzatullin.service.UserService;
import dev.gizzatullin.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/update")
@RequiredArgsConstructor
public class UpdateEntityController {
    private final VehicleService vehicleService;
    private final UserService userService;
    private final RepairRequestService repairRequestService;
    private final SparePartService sparePartService;
    private final RepairService repairService;

    @GetMapping
    public String showUpdateEntityPage() {
        return "update-entity";
    }

    @GetMapping("/vehicle")
    public String showUpdateVehiclePage() {
        return "update-vehicle"; // Шаблон для добавления автомобиля
    }

    @PutMapping("/vehicle")
    public String updateVehicle(@RequestParam("id") Long id,
                                @RequestParam("type.id") Long typeId,
                                @RequestParam("type.name") String typeName,
                                @RequestParam("type.maintenanceInterval") Integer maintenanceInterval,
                                @RequestParam("mileage") Integer mileage,
                                @RequestParam("vehicleStatus") String vehicleStatus,
                                @RequestParam("licensePlate") String licensePlate) {

        VehicleType vehicleType = new VehicleType();
        vehicleType.setId(typeId);
        vehicleType.setName(VehicleTypeName.valueOf(typeName));
        vehicleType.setMaintenanceInterval(maintenanceInterval);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setType(vehicleType);
        vehicle.setMileage(mileage);
        vehicle.setVehicleStatus(VehicleStatus.valueOf(vehicleStatus));
        vehicle.setLicensePlate(licensePlate);

        vehicleService.updateVehicle(vehicle);  // Сохраняем автомобиль в базе данных

        return "redirect:/";  // Перенаправляем на главную страницу
    }

    @GetMapping("/repair")
    public String showUpdateRepairPage() {
        return "update-repair"; // Шаблон для добавления ремонта
    }

    @PutMapping("/repair")
    public String updateRepair(
            @RequestParam("id") Long id,
            @RequestParam("vehicle.id") Long vehicleId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("master.id") Long masterId,
            @RequestParam("request.id") Long requestId,
            @RequestParam("type") String type,
            @RequestParam("status") String status,
            @RequestParam("cost") Double cost,
            @RequestParam("sparePartsId") List<Long> sparePartsId, // Список ID запчастей
            @RequestParam("sparePartsQuantity") List<Integer> sparePartsQuantity // Список количества запчастей
    ) {
        // Создаем объект Repair
        Repair repair = new Repair();
        repair.setId(id);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        repair.setVehicle(vehicle);
        repair.setStartDate(startDate);

        User user = new User();
        user.setId(masterId);
        repair.setMaster(user);
        RepairRequest request = new RepairRequest();
        request.setId(requestId);
        repair.setRequest(request);
        repair.setType(RepairType.valueOf(type));
        repair.setStatus(RepairStatus.valueOf(status));
        repair.setCost(cost);


        // Обрабатываем список запчастей
        Set<SparePart> spareParts = new HashSet<>();
        for (int i = 0; i < sparePartsId.size(); i++) {
            SparePart sparePart = new SparePart();
            sparePart.setId(sparePartsId.get(i));
            sparePart.setStockQuantity(sparePartsQuantity.get(i));
            spareParts.add(sparePart);
        }

        repair.setSpareParts(spareParts);

        // Сохраняем ремонт в базе данных
        repairService.updateRepair(repair);

        return "redirect:/"; // Перенаправление после успешного добавления
    }

    @GetMapping("/spare-part")
    public String showUpdateSparePartPage() {
        return "update-spare-part"; // Шаблон для добавления запчасти
    }

    @PutMapping("/spare-part")
    public String updateSparePart(@RequestParam("id") Long id,
                                  @RequestParam("name") String name,
                                  @RequestParam("stockQuantity") Integer stockQuantity,
                                  @RequestParam("price") Double price) {

        SparePart sparePart = new SparePart();
        sparePart.setId(id);
        sparePart.setName(name);
        sparePart.setStockQuantity(stockQuantity);
        sparePart.setPrice(price);

        sparePartService.updateSparePart(sparePart);

        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUpdateUserPage() {
        return "update-user"; // Шаблон для добавления запчасти
    }

    @PutMapping("/user")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("role") String role) {

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(UserRole.valueOf(role));

        userService.update(id, user);

        return "redirect:/";
    }

    @GetMapping("/repair-request")
    public String showUpdateRepairRequestPage() {
        return "update-repair-request"; // Шаблон для добавления запчасти
    }

    @PutMapping("/repair-request")
    public String updateRepairRequest(@RequestParam("id") Long id,
                                      @RequestParam("user.id") Long userId,
                                      @RequestParam("vehicle.id") Long vehicleId,
                                      @RequestParam("comment") String comment,
                                      @RequestParam("status") String status) {

        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setId(id);

        User user = new User();
        user.setId(userId);
        repairRequest.setUser(user);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        repairRequest.setVehicle(vehicle);

        repairRequest.setComment(comment);
        repairRequest.setStatus(RequestStatus.valueOf(status));

        repairRequestService.updateRepairRequest(repairRequest);

        return "redirect:/";
    }
}
