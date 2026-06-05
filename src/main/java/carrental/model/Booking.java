package carrental.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a booking period for a car. It stores the start/end dates,
 * calculates the total price based on the car's daily rate and the number of days,
 * and tracks whether the booking is still active.
 */
public class Booking {
    private final String bookingId;
    private final Car car;
    private final String customerName;
    private final String phone;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double totalPrice;
    private boolean active;

    public Booking(String bookingId, Car car, String customerName, String phone, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.car = car;
        this.customerName = customerName;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = calculatePrice();
        this.active = true;
    }

    private double calculatePrice() {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; // inclusive
        return car.calculatePrice((int) days);
    }

    public String getBookingId() { return bookingId; }
    public Car getCar() { return car; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }
    public boolean isActive() { return active; }
    public void cancel() { this.active = false; }
}
