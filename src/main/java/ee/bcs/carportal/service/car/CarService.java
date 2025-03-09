package ee.bcs.carportal.service.car;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.persistence.car.CarMapper;
import ee.bcs.carportal.persistence.car.CarRepository;
import ee.bcs.carportal.persistence.fueltype.FuelType;
import ee.bcs.carportal.persistence.fueltype.FuelTypeRepository;
import ee.bcs.carportal.persistence.manufacturer.Manufacturer;
import ee.bcs.carportal.persistence.manufacturer.ManufacturerRepository;
import ee.bcs.carportal.service.car.dto.CarDto;
import ee.bcs.carportal.service.car.dto.CarInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ManufacturerRepository manufacturerRepository;
    private final FuelTypeRepository fuelTypeRepository;

    public CarInfo findCarInfo(Integer carId){
        if(carId == null){
            return null;
        }

        Car car = carRepository.getReferenceById(carId);

        return carMapper.toCarInfo(car);
    }

    public List<CarInfo> getAllCars() {
        List<Car> cars = carRepository.findAll();

        List<CarInfo> carInfos = carMapper.toCarInfos(cars);

        return carInfos;
    }

    public List<Car> findCarsInPriceRange(Integer from, Integer to) {
        return carRepository.findCarsBy(from, to);
    }

    public List<Car> findCarsInPriceRangeWithFuelType(Integer from, Integer to, String fuelTypeCode ) {
        return carRepository.findCarsBy(from, to, fuelTypeCode);
    }

    public void addCar(CarDto carDto) {
        Manufacturer manufacturer = fetchManufacturerById(carDto.getManufacturerId());
        FuelType fuelType = fetchFuelTypeById(carDto.getFuelType());

        Car car = createCar(carDto, manufacturer, fuelType);

        carRepository.save(car);
    }

    // Aitab leida Manufactureri ID järgi
    private Manufacturer fetchManufacturerById(Integer manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }

    // Aitab leida FuelType ID järgi
    private FuelType fetchFuelTypeById(Integer fuelTypeId) {
        return fuelTypeRepository.findById(fuelTypeId)
                .orElseThrow(() -> new RuntimeException("FuelType not found"));
    }

    private Car createCar(CarDto carDto, Manufacturer manufacturer, FuelType fuelType) {
        Car car = new Car();
        car.setManufacturer(manufacturer);
        car.setFuelType(fuelType);
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setEmissions(carDto.getEmissions());
        car.setPrice(carDto.getPrice());
        return car;
    }
}
