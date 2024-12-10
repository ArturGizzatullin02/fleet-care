package dev.gizzatullin.controller;

import dev.gizzatullin.model.repair.Repair;
import dev.gizzatullin.model.request.RepairRequest;
import dev.gizzatullin.model.sparepart.SparePart;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.model.vehicle.Vehicle;
import dev.gizzatullin.service.RepairRequestService;
import dev.gizzatullin.service.RepairService;
import dev.gizzatullin.service.SparePartService;
import dev.gizzatullin.service.UserService;
import dev.gizzatullin.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final VehicleService vehicleService;
    private final UserService userService;
    private final RepairRequestService repairRequestService;
    private final SparePartService sparePartService;
    private final RepairService repairService;

    @GetMapping
    public String showSelectViewPage() {
        return "select-view"; // Возвращает страницу выбора
    }

    @GetMapping("/vehicles")
    public String showVehiclesView(Model model) {
        Collection<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "view-vehicles"; // Страница с автомобилями
    }

    @GetMapping("/users")
    public String showUsersView(Model model) {
        Collection<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "view-users"; // Страница с пользователями
    }

    @GetMapping("/repair-requests")
    public String showRepairRequestsView(Model model) {
        Collection<RepairRequest> repairRequests = repairRequestService.getAllRepairRequests();
        model.addAttribute("repairRequests", repairRequests);
        return "view-repair-requests"; // Страница с запросами на ремонт
    }

    @GetMapping("/spare-parts")
    public String showSparePartsView(Model model) {
        Collection<SparePart> spareParts = sparePartService.getAllSpareParts();
        model.addAttribute("spareParts", spareParts);
        return "view-spare-parts"; // Страница с запчастями
    }

    @GetMapping("/repairs")
    public String showRepairsView(Model model) {
        Collection<Repair> repairs = repairService.getAllRepairs();
        model.addAttribute("repairs", repairs);
        return "view-repairs"; // Страница с ремонтами
    }
}
