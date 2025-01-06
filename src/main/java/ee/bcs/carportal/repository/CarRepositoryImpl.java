package ee.bcs.carportal.repository;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.persistence.FuelType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private Map<Integer, Car> cars = createCars();

    @Override
    public void save(Car car) {
        cars.put(car.getId(), car);
    }

    @Override
    public Car getById(int id) {
        return cars.get(id);
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public void deleteById(int id) {
        cars.remove(id);
    }

    public void resetData() {
        cars = createCars();
    }

    private Map<Integer, Car> createCars() {
        Map<Integer, Car> initialCars = new HashMap<>();
        initialCars.put(1, new Car(1, "Model 3", "Tesla", 2020, FuelType.ELECTRIC, 0.0, 44000));
        initialCars.put(2, new Car(2, "Civic", "Honda", 2021, FuelType.PETROL, 0.05, 25000));
        initialCars.put(3, new Car(3, "Camry", "Toyota", 2022, FuelType.PETROL, 0.04, 28000));
        initialCars.put(4, new Car(4, "F-150", "Ford", 2023, FuelType.PETROL, 0.1, 45000));
        initialCars.put(5, new Car(5, "Prius", "Toyota", 2020, FuelType.HYBRID, 0.03, 30000));
        return initialCars;
    }
}
