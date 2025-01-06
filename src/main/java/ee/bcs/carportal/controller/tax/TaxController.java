package ee.bcs.carportal.controller.tax;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.service.tax.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @GetMapping("/tax/car/{carId}/registration-tax")
    public String getCarRegistrationTaxByCarId(@PathVariable int carId, @RequestParam int baseYear) {
        return taxService.getCarRegistrationTaxByCarId(carId, baseYear);
    }

    @GetMapping("/tax/car/{carId}/annual-tax")
    public String getCarAnnualTaxByCarId(@PathVariable int carId, @RequestParam int baseYear) {
        return taxService.getCarAnnualTaxByCarId(carId, baseYear);
    }


    @GetMapping("/tax/cars/registration-tax-range")
    public List<Car> getCarsByRegistrationTaxRange(@RequestParam int from, @RequestParam int to, @RequestParam int baseYear) {
        return taxService.getCarsByRegistrationTaxRange(from, to, baseYear);
    }

    @GetMapping("/tax/cars/annual-tax-range")
    public List<Car> getCarsByAnnualTaxRange(@RequestParam int from, @RequestParam int to, @RequestParam int baseYear) {
        return taxService.getCarsByAnnualTaxRange(from, to, baseYear);
    }
}
