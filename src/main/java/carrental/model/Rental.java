package carrental.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Rental {
    private final String rentalId = UUID.randomUUID().toString();
    public String getRentalId() { return rentalId; }
    public enum Status { ACTIVE, RETURNED }

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    private Car car;
    private Customer customer;
    private int days;
    private double totalPrice;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private Status status;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.totalPrice = car.calculatePrice(days);
        this.rentedAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public Car getCar()           { return car; }
    public Customer getCustomer() { return customer; }
    public int getDays()          { return days; }
    public double getTotalPrice() { return totalPrice; }
    public Status getStatus()     { return status; }

    public String getRentedAtStr()   { return rentedAt != null ? rentedAt.format(FMT) : "-"; }
    public String getReturnedAtStr() { return returnedAt != null ? returnedAt.format(FMT) : "-"; }
    public LocalDateTime getRentedAt() { return rentedAt; }


    public void markReturned() {
        this.returnedAt = LocalDateTime.now();
        this.status = Status.RETURNED;
    }
}
