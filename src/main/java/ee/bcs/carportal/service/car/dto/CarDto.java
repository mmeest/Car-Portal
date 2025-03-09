package ee.bcs.carportal.service.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    Integer manufacturerId;
    Integer fuelType;
    String model;
    Integer year;
    BigDecimal emissions;
    Integer price;
}
