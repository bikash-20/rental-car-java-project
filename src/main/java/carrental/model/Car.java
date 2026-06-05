package carrental.model;

public class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private String category;
    private boolean available;

    public Car(String carId, String brand, String model, double basePricePerDay, String category) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.category = category;
        this.available = true;
    }

    public String getCarId()           { return carId; }
    public String getBrand()           { return brand; }
    public String getModel()           { return model; }
    public double getBasePricePerDay() { return basePricePerDay; }
    public String getCategory()        { return category; }
    public boolean isAvailable()       { return available; }

    public double calculatePrice(int days) { return basePricePerDay * days; }
    public void rent()      { available = false; }
    public void returnCar() { available = true; }

    public String getDisplayName() { return brand + " " + model; }
}
