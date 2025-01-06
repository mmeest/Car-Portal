package ee.bcs.carportal.controller.car;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.service.car.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/car")
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @GetMapping("/cars/all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/price-range")
    public List<Car> getCarsInPriceRange(@RequestParam int from, @RequestParam int to) {
        return carService.getCarsInPriceRange(from, to);
    }

    @GetMapping("/cars/green/price-range")
    public List<Car> getGreenCarsInPriceRange(@RequestParam int from, @RequestParam int to) {
        return carService.getGreenCarsInPriceRange(from, to);
    }


    @GetMapping("/car/random/basic-info")
    public Car getRandomCarBasicInfo() {
        return carService.getRandomCarBasicInfo();
    }

    @GetMapping("/car/random/detailed-info")
    public Car getRandomCarDetailedInfo() {
        return carService.getRandomCarDetailedInfo();
    }

    @GetMapping("/car/{carId}/basic-info")
    public Car getCarBasicInfoByCarId(@PathVariable int carId) {
        return carService.getCarBasicInfoByCarId(carId);
    }

    @GetMapping("/car/{carId}/detailed-info")
    public Car getCarDetailedInfoByCarId(@PathVariable int carId) {
        return carService.getCarDetailedInfoByCarId(carId);
    }

    @PutMapping("/car/{carId}")
    public void updateCar(@PathVariable int carId, @RequestBody Car car) {
        carService.updateCar(carId, car);
    }

    @PatchMapping("/car/{carId}")
    public void updateCarPrice(@PathVariable int carId, @RequestParam int price) {
        carService.updateCarPrice(carId, price);
    }

    @DeleteMapping("/car/{carId}")
    public void deleteCar(@PathVariable int carId) {
        carService.deleteCar(carId);
    }

}
