package ee.bcs.carportal.repository;

import ee.bcs.carportal.persistence.car.Car;

import java.util.List;

public interface CarRepository {

    void save(Car car);

    Car getById(int id);

    List<Car> findAll();

    void deleteById(int id);

}

