package carrental.service;

import carrental.model.Car;
import carrental.model.Customer;
import carrental.model.Rental;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarRentalSystem {

    private final List<Car>      cars      = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Rental>   rentals   = new ArrayList<>();

    public CarRentalSystem() {
        cars.add(new Car("C001", "Toyota",   "Camry",    60.0,  "Sedan"));
        cars.add(new Car("C002", "Honda",    "Accord",   70.0,  "Sedan"));
        cars.add(new Car("C003", "Mahindra", "Thar",    150.0,  "SUV"));
        cars.add(new Car("C004", "Ford",     "Mustang", 200.0,  "Sports"));
        cars.add(new Car("C005", "BMW",      "X5",      250.0,  "Luxury"));
        cars.add(new Car("C006", "Hyundai",  "Creta",    80.0,  "SUV"));
    }

    // ── Cars ──────────────────────────────────────────────────────────────────
    public List<Car> getAllCars()       { return List.copyOf(cars); }
    public List<Car> getAvailableCars() {
        return cars.stream().filter(Car::isAvailable).collect(Collectors.toList());
    }
    public List<Car> getRentedCars() {
        return cars.stream().filter(c -> !c.isAvailable()).collect(Collectors.toList());
    }
    public Optional<Car> findCarById(String id) {
        return cars.stream().filter(c -> c.getCarId().equalsIgnoreCase(id)).findFirst();
    }

    // ── Customers ─────────────────────────────────────────────────────────────
    public Customer addCustomer(String name, String phone) {
        String id = "CUS" + String.format("%03d", customers.size() + 1);
        Customer c = new Customer(id, name, phone);
        customers.add(c);
        return c;
    }

    // ── Rentals ───────────────────────────────────────────────────────────────
    public Rental rentCar(String carId, String name, String phone, int days) {
        Car car = findCarById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + carId));
        if (!car.isAvailable())
            throw new IllegalStateException("Car " + carId + " is not available.");
        if (days <= 0)
            throw new IllegalArgumentException("Days must be at least 1.");

        Customer customer = addCustomer(name, phone);
        car.rent();
        Rental rental = new Rental(car, customer, days);
        rentals.add(rental);
        return rental;
    }

    public Rental returnCar(String carId) {
        Car car = findCarById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + carId));
        Rental rental = rentals.stream()
                .filter(r -> r.getCar() == car && r.getStatus() == Rental.Status.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active rental for car " + carId));
        car.returnCar();
        rental.markReturned();
        return rental;
    }

    // ── Stats ─────────────────────────────────────────────────────────────────
    public int totalCars()     { return cars.size(); }
    public int availableCars() { return (int) cars.stream().filter(Car::isAvailable).count(); }
    public int rentedCars()    { return (int) cars.stream().filter(c -> !c.isAvailable()).count(); }
    public double totalRevenue() {
        return rentals.stream().mapToDouble(Rental::getTotalPrice).sum();
    }

    public List<Rental> getActiveRentals()  {
        return rentals.stream().filter(r -> r.getStatus() == Rental.Status.ACTIVE).collect(Collectors.toList());
    }
    public List<Rental> getRentalHistory()  {
        return rentals.stream().filter(r -> r.getStatus() == Rental.Status.RETURNED).collect(Collectors.toList());
    }
    public List<Rental> getAllRentals() { return List.copyOf(rentals); }
}
