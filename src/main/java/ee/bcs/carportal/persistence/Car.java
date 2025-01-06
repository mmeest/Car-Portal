package ee.bcs.carportal.persistence;

import java.util.Objects;

public class Car {
    private Integer id;
    private String carModel;
    private String manufacturer;
    private int modelYear;
    private FuelType fuelType;
    private double emissions;
    private int price;

    public Car(Integer id, String carModel, String manufacturer, int modelYear, FuelType fuelType, double emissions, int price) {
        this.id = id;
        this.carModel = carModel;
        this.manufacturer = manufacturer;
        this.modelYear = modelYear;
        this.fuelType = fuelType;
        this.emissions = emissions;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getEmissions() {
        return emissions;
    }

    public void setEmissions(double emissions) {
        this.emissions = emissions;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return modelYear == car.modelYear && Double.compare(emissions, car.emissions) == 0 && price == car.price && Objects.equals(id, car.id) && Objects.equals(carModel, car.carModel) && Objects.equals(manufacturer, car.manufacturer) && fuelType == car.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carModel, manufacturer, modelYear, fuelType, emissions, price);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carModel='" + carModel + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", modelYear=" + modelYear +
                ", fuelType=" + fuelType +
                ", emissions=" + emissions +
                ", price=" + price +
                '}';
    }

}