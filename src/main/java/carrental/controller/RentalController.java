package carrental.controller;

import carrental.model.Car;
import carrental.model.Rental;
import carrental.service.CarRentalSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RentalController {

    private final CarRentalSystem system;

    public RentalController(CarRentalSystem system) {
        this.system = system;
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("cars",        system.getAllCars());
        model.addAttribute("totalCars",   system.totalCars());
        model.addAttribute("available",   system.availableCars());
        model.addAttribute("rented",      system.rentedCars());
        model.addAttribute("revenue",     system.totalRevenue());
        return "dashboard";
    }

    // ── Rent ──────────────────────────────────────────────────────────────────
    @GetMapping("/rent")
    public String rentPage(Model model) {
        model.addAttribute("availableCars", system.getAvailableCars());
        return "rent";
    }

    @PostMapping("/rent")
    public String doRent(@RequestParam String carId,
                         @RequestParam String name,
                         @RequestParam String phone,
                         @RequestParam int days,
                         RedirectAttributes ra) {
        try {
            Rental rental = system.rentCar(carId, name, phone, days);
            ra.addFlashAttribute("rental", rental);
            ra.addFlashAttribute("success", true);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            ra.addFlashAttribute("availableCars", system.getAvailableCars());
        }
        return "redirect:/rent/success";
    }

    @GetMapping("/rent/success")
    public String rentSuccess(Model model) {
        if (!model.containsAttribute("success")) return "redirect:/rent";
        return "rent-success";
    }

    // ── Return ────────────────────────────────────────────────────────────────
    @GetMapping("/return")
    public String returnPage(Model model) {
        model.addAttribute("rentedCars", system.getRentedCars());
        model.addAttribute("activeRentals", system.getActiveRentals());
        return "return";
    }

    @PostMapping("/return")
    public String doReturn(@RequestParam String carId, RedirectAttributes ra) {
        try {
            Rental rental = system.returnCar(carId);
            ra.addFlashAttribute("rental", rental);
            ra.addFlashAttribute("success", true);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/return/success";
    }

    @GetMapping("/return/success")
    public String returnSuccess(Model model) {
        if (!model.containsAttribute("success")) return "redirect:/return";
        return "return-success";
    }

    // ── History ───────────────────────────────────────────────────────────────
    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("activeRentals",  system.getActiveRentals());
        model.addAttribute("returnedRentals", system.getRentalHistory());
        model.addAttribute("totalRevenue",   system.totalRevenue());
        model.addAttribute("totalCount",     system.getAllRentals().size());
        return "history";
    }
}
