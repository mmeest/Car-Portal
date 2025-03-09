package ee.bcs.carportal.persistence.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("select c from Car c where c.price between :priceFrom and :priceTo order by c.price")
    List<Car> findCarsBy(Integer priceFrom, Integer priceTo);

    @Query("""
            select c from Car c
            where c.price between :priceFrom and :priceTo and c.fuelType.code = :fuelTypeCode
            order by c.price""")
    List<Car> findCarsBy(Integer priceFrom, Integer priceTo, String fuelTypeCode);

}