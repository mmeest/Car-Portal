package ee.bcs.carportal.persistence.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("select c from Car c where c.price between :priceFrom and :priceTo order by c.price")
    List<Car> findCarsBy(Integer priceFrom, Integer priceTo);

    @Query("""
            SELECT c
            FROM Car c
            WHERE c.price
            BETWEEN :priceFrom
            AND :priceTo
            AND c.fuelType.code = :fuelTypeCode
            ORDER BY c.price""")
    List<Car> findCarsBy(Integer priceFrom, Integer priceTo, String fuelTypeCode);

    @Query("""
            SELECT (count(c) > 0)
            FROM Car c
            WHERE c.manufacturer.id = :manufacturerId
            AND c.model = :model
            AND c.year = :year
            """)
    boolean carExistsBy(Integer manufacturerId, String model, Integer year);
}