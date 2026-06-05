package carrental.controller;

import carrental.model.Car;
import carrental.model.Rental;
import carrental.model.Booking;
import carrental.service.CarRentalSystem;
import carrental.service.PdfService;
import carrental.service.RevenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class RentalController {

    private final CarRentalSystem system;
    private final PdfService pdfService;
    private final RevenueService revenueService;

    public RentalController(CarRentalSystem system, PdfService pdfService, RevenueService revenueService) {
        this.system = system;
        this.pdfService = pdfService;
        this.revenueService = revenueService;
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

    // ── Rent (now uses booking flow) ────────────────────────────────────────
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

    // ── Booking ───────────────────────────────────────────────────────────────
    @GetMapping("/booking")
    public String bookingPage(Model model) {
        model.addAttribute("availableCars", system.getAvailableCars());
        return "booking";
    }

    @PostMapping("/booking")
    public String doBooking(@RequestParam String carId,
                            @RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String startDate,
                            @RequestParam String endDate,
                            RedirectAttributes ra) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end   = LocalDate.parse(endDate);
            Booking booking = system.createBooking(carId, name, phone, start, end);
            // Convert to rental (days inclusive)
            int days = (int) (java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1);
            Rental rental = system.rentCar(carId, name, phone, days);
            ra.addFlashAttribute("booking", booking);
            ra.addFlashAttribute("rental", rental);
            ra.addFlashAttribute("success", true);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            ra.addFlashAttribute("availableCars", system.getAvailableCars());
        }
        return "redirect:/booking/success";
    }

    @GetMapping("/booking/success")
    public String bookingSuccess(Model model) {
        if (!model.containsAttribute("success")) return "redirect:/booking";
        return "booking-success";
    }

    // ── PDF Receipt ────────────────────────────────────────────────────────
    @GetMapping("/receipt/{rentalId}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable String rentalId) throws Exception {
        Rental rental = system.getAllRentals().stream()
                .filter(r -> r.getRentalId().equals(rentalId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));
        byte[] pdf = pdfService.generateReceipt(rental);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "receipt-" + rentalId + ".pdf");
        return new ResponseEntity<>(pdf, headers, 200);
    }

    // ── Revenue JSON ───────────────────────────────────────────────────
    @GetMapping("/reports/revenue/monthly")
    @ResponseBody
    public Map<String, Double> monthlyRevenue() {
        return revenueService.monthlyRevenue();
    }

    @GetMapping("/reports/revenue/categories")
    @ResponseBody
    public Map<String, Double> categoryRevenue() {
        return revenueService.categoryRevenue();
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

    // ── Reports page ───────────────────────────────────────────────────────
    @GetMapping("/reports")
    public String reportsPage() {
        return "reports";
    }
}
