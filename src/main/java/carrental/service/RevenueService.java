package carrental.service;

import carrental.model.Rental;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RevenueService {
    private final CarRentalSystem system;

    public RevenueService(CarRentalSystem system) {
        this.system = system;
    }

    public Map<String, Double> monthlyRevenue() {
        return system.getAllRentals().stream()
                .filter(r -> r.getStatus() == Rental.Status.RETURNED)
                .collect(Collectors.groupingBy(
                        r -> YearMonth.from(r.getRentedAt()).toString(),
                        Collectors.summingDouble(Rental::getTotalPrice)));
    }

    public Map<String, Double> categoryRevenue() {
        return system.getAllRentals().stream()
                .filter(r -> r.getStatus() == Rental.Status.RETURNED)
                .collect(Collectors.groupingBy(
                        r -> r.getCar().getCategory(),
                        Collectors.summingDouble(Rental::getTotalPrice)));
    }
}
