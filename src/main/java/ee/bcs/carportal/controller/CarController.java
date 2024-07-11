package ee.bcs.carportal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RequestMapping("/api/v1")
@RestController
public class CarController {

    private final String[] carModels = {"Model 3", "Civic", "Camry", "F-150", "Prius"};
    private final String[] manufacturers = {"Tesla", "Honda", "Toyota", "Ford", "Toyota"};
    private final int[] modelYears = {2020, 2021, 2022, 2023, 2020};
    private final String[] fuelTypes = {"Electric", "Petrol", "Petrol", "Petrol", "Hybrid"};
    private final double[] emissions = {0.0, 0.05, 0.04, 0.1, 0.03};
    private final int[] prices = {44000, 25000, 28000, 45000, 30000};

    // Fuel type constants
    private static final String FUEL_TYPE_ELECTRIC = "Electric";
    private static final String FUEL_TYPE_HYBRID = "Hybrid";
    private static final String FUEL_TYPE_PETROL = "Petrol";

    public static final int ANNUAL_TAX_BASE_FEE = 50;

    // Mandatory endpoints go to below
    // Please use @Tag annotation as below with all mandatory endpoints:
    // @Tag(name = "Mandatory")

    @GetMapping("/cars/all")
    @Tag(name = "Mandatory")
    public String getAllCars() {
        int carCounter = 0;
        StringBuilder sb = new StringBuilder("All car models: ");
        for (String model : carModels) {
            sb.append(model).append(", ");
            carCounter++;
        }
        return sb.substring(0, sb.length() - 2) + "; (number of car models: " + carCounter + ")";
    }

    @GetMapping("/cars/price-range")
    @Tag(name = "Mandatory")
    public String getCarsInPriceRange(@RequestParam int from, @RequestParam int to) {
        int carCounter = 0;
        StringBuilder sb = new StringBuilder("Cars in price range €" + from + " - €" + to + ":");
        for (int carId = 0; carId < prices.length; carId++) {
            if (prices[carId] >= from && prices[carId] <= to) {
                sb.append("\n\n").append(getCarDetailedInfoByCarId(carId));
                carCounter++;
            }
        }
        if (carCounter == 0) {
            return "No cars found in price range €" + from + " - €" + to;
        }
        return sb + "\n\nNumber of car models: " + carCounter;
    }

    @GetMapping("/cars/green/price-range")
    @Tag(name = "Mandatory")
    public String getGreenCarsInPriceRange(@RequestParam int from, @RequestParam int to) {
        int carCounter = 0;
        StringBuilder sb = new StringBuilder("Green cars in price range €" + from + " - €" + to + ": ");
        for (int carId = 0; carId < prices.length; carId++) {
            if (prices[carId] >= from && prices[carId] <= to && (fuelTypes[carId].equals(FUEL_TYPE_ELECTRIC) || fuelTypes[carId].equals(FUEL_TYPE_HYBRID))) {
                sb.append(carModels[carId]).append(" (").append(fuelTypes[carId]).append("), ");
                carCounter++;
            }
        }
        return sb.substring(0, sb.length() - 2) + "; (number of car models: " + carCounter + ")";
    }

    @GetMapping("/car/{carId}/registration-tax")
    @Tag(name = "Mandatory")
    public String getCarRegistrationTaxByCarId(@PathVariable int carId, @RequestParam int baseYear) {
        double registrationTaxRate = calculateRegistrationTaxRate(carId, baseYear);
        double registrationTaxAmount = calculateTaxAmount(registrationTaxRate, prices[carId]);
        return "The registration tax rate for " + modelYears[carId] + " " + manufacturers[carId] + " "
                + carModels[carId] + " is " + registrationTaxRate + "% with total tax amount €" + String.format("%.0f", registrationTaxAmount);
    }

    @GetMapping("/car/{carId}/annual-tax")
    @Tag(name = "Mandatory")
    public String getCarAnnualTaxByCarId(@PathVariable int carId, @RequestParam int baseYear) {
        double annualTax = calculateAnnualTax(carId, baseYear);
        return String.format("The annual tax for %d %s %s is €%.0f", modelYears[carId], manufacturers[carId], carModels[carId], annualTax);
    }

    @GetMapping("/car/random/basic-info")
    @Tag(name = "Mandatory")
    public String getRandomCarBasicInfo() {
        int carId = new Random().nextInt(0, 5);
        return getCarInfo(manufacturers[carId], carModels[carId], modelYears[carId]);
    }

    @GetMapping("/car/random/detailed-info")
    @Tag(name = "Mandatory")
    public String getRandomCarDetailedInfo() {
        int carId = new Random().nextInt(0, 5);
        try {
            return getCarInfo(carId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/car/{carId}/basic-info")
    @Tag(name = "Mandatory")
    public String getCarBasicInfoByCarId(@PathVariable int carId) {
        try {
            return getCarInfo(manufacturers[carId], carModels[carId], modelYears[carId]);
        } catch (IndexOutOfBoundsException e) {
            return "No car with id " + carId + " exists";
        }
    }

    @GetMapping("/car/{carId}/detailed-info")
    @Tag(name = "Mandatory")
    public String getCarDetailedInfoByCarId(@PathVariable int carId) {
        try {
            return getCarInfo(carId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Extra practice endpoints go to below
    // Please use @Tag annotation as below with all extra practice endpoints:
    // @Tag(name = "Extra practice")

    // ALL PRIVATE METHODS go to below
    private double calculateRegistrationTaxRate(int carId, int baseYear) {
        double registrationTaxRate = getFuelTypeAdjustedRegistrationTaxRate(fuelTypes[carId]);
        registrationTaxRate = adjustRegistrationTaxRateForEmissions(registrationTaxRate, emissions[carId]);
        registrationTaxRate = adjustRegistrationTaxRateForModelYear(registrationTaxRate, baseYear, modelYears[carId]);

        // apply rounding to one decimal place
        return roundToOneDecimalPlace(registrationTaxRate);
    }

    private double getFuelTypeAdjustedRegistrationTaxRate(String fuelType) {
        final double BASE_TAX_RATE = 5.0;
        final double ELECTRIC_ADJUSTMENT = -1.5;
        final double HYBRID_ADJUSTMENT = -0.5;
        final double PETROL_ADJUSTMENT = 1.5;

        double taxRate = BASE_TAX_RATE;

        switch (fuelType) {
            case FUEL_TYPE_ELECTRIC -> taxRate += ELECTRIC_ADJUSTMENT;
            case FUEL_TYPE_HYBRID -> taxRate += HYBRID_ADJUSTMENT;
            case FUEL_TYPE_PETROL -> taxRate += PETROL_ADJUSTMENT;
        }
        return taxRate;
    }

    private double adjustRegistrationTaxRateForEmissions(double taxRate, double emissions) {
        final double EMISSION_MULTIPLIER = 10.0;
        return taxRate + emissions * EMISSION_MULTIPLIER;
    }

    private double adjustRegistrationTaxRateForModelYear(double taxRate, int baseYear, int modelYear) {
        final double MODEL_YEAR_MULTIPLIER = 0.2;
        int yearDifference = baseYear - modelYear;
        return taxRate - yearDifference * MODEL_YEAR_MULTIPLIER;
    }

    private double roundToOneDecimalPlace(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private double calculateTaxAmount(double taxRate, int price) {
        return Math.round(price * (taxRate / 100));
    }

    private double calculateAnnualTax(int carId, int baseYear) {

        // Adjust for fuel type
        double annualTax = getFuelTypeAdjustAnnualTax(fuelTypes[carId]);

        // Adjust for emissions
        annualTax = adjustAnnualTaxForEmissions(annualTax, emissions[carId]);

        // Adjust for model year
        annualTax = adjustAnnualTaxForModelYear(annualTax, baseYear, modelYears[carId]);

        // Ensure the final annual tax is at least the base fee
        return adjustMinimumAnnualTax(annualTax);
    }

    private double getFuelTypeAdjustAnnualTax(String fuelType) {
        final double HYBRID_ADJUSTMENT = 20.0;
        final double PETROL_ADJUSTMENT = 30.0;

        double annualTax = ANNUAL_TAX_BASE_FEE;
        switch (fuelType) {
            case FUEL_TYPE_HYBRID -> annualTax += HYBRID_ADJUSTMENT;
            case FUEL_TYPE_PETROL -> annualTax += PETROL_ADJUSTMENT;
        }
        return annualTax;
    }

    private double adjustAnnualTaxForEmissions(double annualTax, double emissions) {
        final double EMISSION_MULTIPLIER = 500.0;
        return annualTax + emissions * EMISSION_MULTIPLIER;
    }

    private double adjustAnnualTaxForModelYear(double annualTax, int baseYear, int modelYear) {
        final double MODEL_YEAR_MULTIPLIER = 2.0;
        int yearDifference = baseYear - modelYear;
        return annualTax - yearDifference * MODEL_YEAR_MULTIPLIER;
    }

    private double adjustMinimumAnnualTax(double annualTax) {
        if (annualTax < ANNUAL_TAX_BASE_FEE) {
            return ANNUAL_TAX_BASE_FEE;
        }
        return annualTax;
    }


    private String getCarInfo(String manufacturer, String carModel, int modelYear) {
        return "Make: " + manufacturer + "\n" +
                "Model: " + carModel + "\n" +
                "Year: " + modelYear;
    }

    private String getCarInfo(int carId) throws Exception {
        if (carId < 0 || carId >= carModels.length) {
            throw new Exception("No car with id " + carId + " exists");
        }
        return "Make: " + manufacturers[carId] + "\n" +
                "Model: " + carModels[carId] + "\n" +
                "Fuel type: " + fuelTypes[carId] + "\n" +
                "Emission: " + emissions[carId] + "\n" +
                "Price: €" + prices[carId];
    }

}
