package ee.bcs.carportal.service.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CarDetailedInfo extends CarInfo {
    String fuelType;
    BigDecimal emissions;
    Integer price;
}
