package ee.bcs.carportal.service;

import ee.bcs.carportal.persistence.Car;
import ee.bcs.carportal.persistence.FuelType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ee.bcs.carportal.persistence.FuelType.ELECTRIC;
import static ee.bcs.carportal.persistence.FuelType.HYBRID;

@Service
public class CarService {

    private static final double BASE_FEE = 50.0;

    public static List<Car> cars = createCars();

    public static List<Car> createCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Model 3", "Tesla", 2020, ELECTRIC, 0.0, 44000));
        cars.add(new Car("Civic", "Honda", 2021, FuelType.PETROL, 0.05, 25000));
        cars.add(new Car("Camry", "Toyota", 2022, FuelType.PETROL, 0.04, 28000));
        cars.add(new Car("F-150", "Ford", 2023, FuelType.PETROL, 0.1, 45000));
        cars.add(new Car("Prius", "Toyota", 2020, FuelType.HYBRID, 0.03, 30000));
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public List<Car> getCarsInPriceRange(int from, int to) {
        List<Car> carsInPriceRange = new ArrayList<>();
        for (Car car : cars) {
            boolean isWithinPriceRange = car.getPrice() >= from && car.getPrice() <= to;
            if (isWithinPriceRange) {
                carsInPriceRange.add(car);
            }
        }
        return carsInPriceRange;
    }

    public List<Car> getGreenCarsInPriceRange(int from, int to) {
        List<Car> greenCarsInPriceRange = new ArrayList<>();
        for (Car car : cars) {
            boolean isWithinPriceRange = car.getPrice() >= from && car.getPrice() <= to;
            boolean isGreenCar = car.getFuelType().equals(ELECTRIC) || car.getFuelType().equals(HYBRID);
            if (isWithinPriceRange && isGreenCar) {
                greenCarsInPriceRange.add(car);
            }
        }
        return greenCarsInPriceRange;
    }

    public String getCarRegistrationTaxByCarId(int carId, int baseYear) {
        double registrationTaxRate = calculateRegistrationTaxRate(carId, baseYear);
        double taxAmount = calculateTaxAmount(carId, registrationTaxRate);
        return "The registration tax rate for " + cars.get(carId).getModelYear() + " " + cars.get(carId).getManufacturer() + " "
                + cars.get(carId).getCarModel() + " is " + registrationTaxRate + "% with total tax amount €" + String.format("%.0f", taxAmount);
    }

    public String getCarAnnualTaxByCarId(int carId, int baseYear) {
        double annualTax = calculateAnnualTax(carId, baseYear);
        return String.format("The annual tax for %d %s %s is €%.0f", cars.get(carId).getModelYear(), cars.get(carId).getManufacturer(), cars.get(carId).getCarModel(), annualTax);
    }

    public Car getRandomCarBasicInfo() {
        int carId = new Random().nextInt(cars.size());
        return cars.get(carId);
    }

    public Car getRandomCarDetailedInfo() {
        int carId = new Random().nextInt(cars.size());
        return cars.get(carId);
    }

    public Car getCarBasicInfoByCarId(int carId) {
        return cars.get(carId);
    }

    public Car getCarDetailedInfoByCarId(int carId) {
        return cars.get(carId);
    }

    public void updateCar(int carId, Car car) {
        cars.set(carId, car);
    }

    public void updateCarPrice(int carId, int price) {
        Car car = cars.get(carId);
        car.setPrice(price);
    }

    public List<Car> getCarsByRegistrationTaxRange(int from, int to, int baseYear) {
        int carId = 0;
        List<Car> carsInRegistrationTaxRange = new ArrayList<>();
        for (Car car : cars) {
            double taxRate = calculateRegistrationTaxRate(carId, baseYear);
            double taxAmount = calculateTaxAmount(carId, taxRate);
            if (taxAmount >= from && taxAmount <= to) {
                carsInRegistrationTaxRange.add(car);
            }
            carId++;
        }
        return carsInRegistrationTaxRange;
    }

    public List<Car> getCarsByAnnualTaxRange(int from, int to, int baseYear) {
        int carId = 0;
        List<Car> carsInAnnualTaxRange = new ArrayList<>();
        double annualTax = calculateAnnualTax(carId, baseYear);
        for (Car car : cars) {
            if (annualTax >= from && annualTax <= to) {
                carsInAnnualTaxRange.add(car);
            }
            carId++;
        }
        return carsInAnnualTaxRange;
    }

    private double calculateRegistrationTaxRate(int carId, int baseYear) {
        double taxRate = getFuelTypeAdjustedRegistrationTaxRate(carId);
        taxRate = getEmissionsAdjustedRegistrationTaxRate(carId, taxRate);
        taxRate = getModelYearAdjustedRegistrationTaxRate(carId, baseYear, taxRate);
        return getRoundedRegistrationTaxRate(taxRate);
    }

    private double getFuelTypeAdjustedRegistrationTaxRate(int carId) {
        final double BASE_TAX_RATE = 5.0;
        final double ELECTRIC_ADJUSTMENT = -1.5;
        final double HYBRID_ADJUSTMENT = -0.5;
        final double PETROL_ADJUSTMENT = 1.5;
        double taxRate = BASE_TAX_RATE; // Base tax rate of 5%

        switch (cars.get(carId).getFuelType()) {
            case ELECTRIC -> taxRate += ELECTRIC_ADJUSTMENT;
            case HYBRID -> taxRate += HYBRID_ADJUSTMENT;
            case PETROL -> taxRate += PETROL_ADJUSTMENT;
        }
        return taxRate;
    }

    private double getEmissionsAdjustedRegistrationTaxRate(int carId, double taxRate) {
        final double EMISSION_MULTIPLIER = 10.0; // For each 0.01 in emissions, increase tax rate by 0.1%
        double emissionAdjustment = cars.get(carId).getEmissions() * EMISSION_MULTIPLIER; // For each 0.01 in emissions, increase tax rate by 0.1%
        taxRate += emissionAdjustment;
        return taxRate;
    }

    private double getModelYearAdjustedRegistrationTaxRate(int carId, int baseYear, double taxRate) {
        final double MODEL_YEAR_MULTIPLIER = 0.2; // Reduce 0.2% for each year older than the base year
        int yearDifference = baseYear - cars.get(carId).getModelYear();
        double modelYearAdjustment = yearDifference * MODEL_YEAR_MULTIPLIER; // Reduce 0.2% for each year older than the base year
        taxRate -= modelYearAdjustment;
        return taxRate;
    }

    private static double getRoundedRegistrationTaxRate(double taxRate) {
        return Math.round(taxRate * 10.0) / 10.0;
    }

    private double calculateTaxAmount(int carId, double registrationTaxRate) {
        return cars.get(carId).getPrice() * (registrationTaxRate / 100);
    }

    private double calculateAnnualTax(int carId, int baseYear) {
        double annualTax = getFuelTypeAdjustedAnnualTax(carId);
        annualTax = getEmissionsAdjustedAnnualTax(carId, annualTax);
        annualTax = getModelYearAdjustedAnnualTax(carId, baseYear, annualTax);
        return getMinimumFeeAdjustedAnnualTax(annualTax);
    }

    private double getFuelTypeAdjustedAnnualTax(int carId) {
        final double HYBRID_ADJUSTMENT = 20.0;
        final double PETROL_ADJUSTMENT = 30.0;
        double annualTax = BASE_FEE; // Base annual tax fee

        switch (cars.get(carId).getFuelType()) {
            case HYBRID -> annualTax += HYBRID_ADJUSTMENT;
            case PETROL -> annualTax += PETROL_ADJUSTMENT;
        }
        return annualTax;
    }

    private double getEmissionsAdjustedAnnualTax(int carId, double annualTax) {
        final double EMISSION_MULTIPLIER = 500.0; // For each 0.01 in emissions, increase tax by €5
        double emissionAdjustment = cars.get(carId).getEmissions() * EMISSION_MULTIPLIER; // For each 0.01 in emissions, increase tax by €5
        annualTax += emissionAdjustment;
        return annualTax;
    }

    private double getModelYearAdjustedAnnualTax(int carId, int baseYear, double annualTax) {
        final double MODEL_YEAR_MULTIPLIER = 2.0; // Reduce €2 for each year older than the base year
        int yearDifference = baseYear - cars.get(carId).getModelYear();
        double modelYearAdjustment = yearDifference * MODEL_YEAR_MULTIPLIER; // Reduce €2 for each year older than the base year
        annualTax -= modelYearAdjustment;
        return annualTax;
    }

    private static double getMinimumFeeAdjustedAnnualTax(double annualTax) {
        boolean annualTaxIsBelowBaseFee = annualTax < BASE_FEE;
        if (annualTaxIsBelowBaseFee) {
            annualTax = BASE_FEE;
        }
        return annualTax;
    }

}
