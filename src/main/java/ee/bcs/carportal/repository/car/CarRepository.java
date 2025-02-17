package ee.bcs.carportal.repository.car;

import ee.bcs.carportal.persistence.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}