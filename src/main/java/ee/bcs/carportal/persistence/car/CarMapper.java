package ee.bcs.carportal.persistence.car;

import ee.bcs.carportal.persistence.fueltype.FuelType;
import ee.bcs.carportal.persistence.manufacturer.Manufacturer;
import ee.bcs.carportal.persistence.manufacturer.ManufacturerRepository;
import ee.bcs.carportal.service.car.dto.CarDetailedInfo;
import ee.bcs.carportal.service.car.dto.CarDto;
import ee.bcs.carportal.service.car.dto.CarInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    //@Named("toCarInfo")
    @Mapping(source = "manufacturer.name", target = "make")
    @Mapping(source = "model", target = "modelName")
    @Mapping(source = "year", target = "releaseYear")
    CarInfo toCarInfo(Car car);

    @Named("toCarDetailedInfo")
    @InheritConfiguration(name = "toCarInfo")
    @Mapping(source = "fuelType", target = "fuelType")
    @Mapping(source = "emissions", target = "emissions")
    @Mapping(source = "price", target = "price")
    CarDetailedInfo toCarDetailedInfo(Car car);

    List<CarInfo> toCarInfos(List<Car> cars);
    List<CarDetailedInfo> toCarDetailedInfos(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "fuelType", ignore = true)
    Car toCar(CarDto carDto);

    @InheritConfiguration(name = "toCar")
    @Mapping(target = "manufacturer", source = "manufacturerId", qualifiedByName = "toManufacturer")
    Car updateCar(CarDto carDto, @MappingTarget Car car);

    default String map(FuelType fuelType) {
        return fuelType != null ? fuelType.getName() : null;
    }

    @Named("toManufacturer")
    default Manufacturer mapToManufacturer(Integer manufacturerId) {
        if (manufacturerId == null) {
            return null;
        }
        return new Manufacturer(manufacturerId);  // Tagastame õigesti loodud objekti
    }
}



