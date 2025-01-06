package ee.bcs.carportal.service.car;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.persistence.FuelType;
import ee.bcs.carportal.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getCarsInPriceRange(int from, int to) {
        List<Car> carsInPriceRange = new ArrayList<>();
        for (Car car : carRepository.findAll()) {
            boolean isWithinPriceRange = car.getPrice() >= from && car.getPrice() <= to;
            if (isWithinPriceRange) {
                carsInPriceRange.add(car);
            }
        }
        return carsInPriceRange;
    }

    public List<Car> getGreenCarsInPriceRange(int from, int to) {
        List<Car> result = new ArrayList<>();
        for (Car car : carRepository.findAll()) {
            if (car.getPrice() >= from && car.getPrice() <= to &&
                    (car.getFuelType() == FuelType.ELECTRIC || car.getFuelType() == FuelType.HYBRID)) {
                result.add(car);
            }
        }
        return result;
    }

    public Car getRandomCarBasicInfo() {
        List<Car> cars = carRepository.findAll();
        int carId = new Random().nextInt(cars.size());
        return cars.get(carId);
    }

    public Car getRandomCarDetailedInfo() {
        List<Car> cars = carRepository.findAll();
        int carId = new Random().nextInt(cars.size());
        return cars.get(carId);
    }

    public Car getCarBasicInfoByCarId(int carId) {
        return carRepository.getById(carId);
    }

    public Car getCarDetailedInfoByCarId(int carId) {
        return carRepository.getById(carId);
    }

    public void updateCar(int carId, Car updatedCar) {
        Car car = carRepository.getById(carId);
        car.setCarModel(updatedCar.getCarModel());
        car.setManufacturer(updatedCar.getManufacturer());
        car.setModelYear(updatedCar.getModelYear());
        car.setFuelType(updatedCar.getFuelType());
        car.setEmissions(updatedCar.getEmissions());
        car.setPrice(updatedCar.getPrice());
        carRepository.save(car);
    }

    public void updateCarPrice(int carId, int price) {
        Car car = carRepository.getById(carId);
        car.setPrice(price);
        carRepository.save(car);
    }

    public void deleteCar(int carId) {
        carRepository.deleteById(carId);
    }

}
