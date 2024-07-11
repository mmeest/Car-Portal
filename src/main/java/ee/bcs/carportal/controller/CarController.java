package ee.bcs.carportal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class CarController {

    private final String[] carModels = {"Model 3", "Civic", "Camry", "F-150", "Prius"};
    private final String[] manufacturers = {"Tesla", "Honda", "Toyota", "Ford", "Toyota"};
    private final int[] modelYears = {2020, 2021, 2022, 2023, 2020};
    private final String[] fuelTypes = {"Electric", "Petrol", "Petrol", "Petrol", "Hybrid"};
    private final double[] emissions = {0.0, 0.05, 0.04, 0.1, 0.03};
    private final int[] prices = {44000, 25000, 28000, 45000, 30000};

    // Mandatory endpoints go to below
    // Please use @Tag annotation as below with all mandatory endpoints:
    // @Tag(name = "Mandatory")

    @GetMapping("/cars/all")
    @Tag(name = "Mandatory")
    public String getAllCars() {
        StringBuilder sb = new StringBuilder("All car models: ");
        for (String model : carModels) {
            sb.append(model).append(", ");
        }
        return sb.substring(0, sb.length() - 2) + ";";
    }

    @GetMapping("/cars/price-range/from/to")
    @Tag(name = "Mandatory")
    public String getCarsInPriceRange() {
        int from = 28000;
        int to = 44000;
        return "from €" + from + " to €" + to;
    }

    // Extra practice endpoints go to below
    // Please use @Tag annotation as below with all extra practice endpoints:
    // @Tag(name = "Extra practice")

    // ALL PRIVATE METHODS go to below


}
