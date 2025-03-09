package ee.bcs.carportal.persistence.car;

import ee.bcs.carportal.service.car.dto.CarDto;
import ee.bcs.carportal.service.car.dto.CarInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(source = "manufacturer.name", target = "make")
    @Mapping(source = "model", target = "modelName")
    @Mapping(source = "year", target = "releaseYear")
    CarInfo toCarInfo(Car car);

    List<CarInfo> toCarInfos(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    Car toCar(CarDto carDto);
}
