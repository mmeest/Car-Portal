package ee.bcs.carportal.service.car;

import ee.bcs.carportal.persistence.FuelType;
import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    Map<Integer, Car> carsMap = new HashMap<>();
    ArrayList<Car> carsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        carsMap.put(1, new Car(1, "Model 3", "Tesla", 2020, FuelType.ELECTRIC, 0.0, 44000));
        carsMap.put(2, new Car(2, "Civic", "Honda", 2021, FuelType.PETROL, 0.05, 25000));
        carsMap.put(3, new Car(3, "Camry", "Toyota", 2022, FuelType.PETROL, 0.04, 28000));
        carsMap.put(4, new Car(4, "F-150", "Ford", 2023, FuelType.PETROL, 0.1, 45000));
        carsMap.put(5, new Car(5, "Prius", "Toyota", 2020, FuelType.HYBRID, 0.03, 30000));
        carsList = new ArrayList<>(carsMap.values());
    }

    // Test Case 1: Adds a new car
    @Test
    void shouldAddNewCar() {
        // Arrange
        Car newCar = new Car(6, "Mustang", "Ford", 2021, FuelType.PETROL, 0.08, 55000);

        // Act
        carService.addCar(newCar);

        // Assert
        verify(carRepository, times(1)).save(newCar);
    }

    // Test Case 2: Returns all cars
    @Test
    void shouldReturnAllCars() {
        // Arrange
        when(carRepository.findAll()).thenReturn(carsList);

        // Act
        List<Car> cars = carService.getAllCars();

        // Assert
        verify(carRepository, times(1)).findAll();
        assertEquals(carsList.size(), cars.size(), "The size of the returned list should match the expected list");
        assertTrue(cars.containsAll(carsList), "The returned list should contain all cars from the expected list");
    }

    // Test Case 3: Returns cars in price range
    @Test
    void shouldReturnCarsInPriceRange() {
        // Arrange
        when(carRepository.findAll()).thenReturn(carsList);

        when(carRepository.getById(1)).thenReturn(carsMap.get(1));
        when(carRepository.getById(2)).thenReturn(carsMap.get(2));
        when(carRepository.getById(3)).thenReturn(carsMap.get(3));
        when(carRepository.getById(4)).thenReturn(carsMap.get(4));
        when(carRepository.getById(5)).thenReturn(carsMap.get(5));

        List<Car> expectedCars = List.of(carsMap.get(2), carsMap.get(3), carsMap.get(5));

        // Act
        List<Car> cars = carService.getCarsInPriceRange(25000, 30000);

        // Assert
        verify(carRepository, times(1)).findAll();
        assertEquals(expectedCars.size(), cars.size(), "The number of cars should match the expected count.");
        assertTrue(cars.containsAll(expectedCars), "The result list should contain all cars from the expected list.");
    }

    // Test Case 4: Returns green cars in price range
    @Test
    void shouldReturnGreenCarsInPriceRange() {
        // Arrange
        when(carRepository.findAll()).thenReturn(carsList);

        when(carRepository.getById(1)).thenReturn(carsMap.get(1));
        when(carRepository.getById(2)).thenReturn(carsMap.get(2));
        when(carRepository.getById(3)).thenReturn(carsMap.get(3));
        when(carRepository.getById(4)).thenReturn(carsMap.get(4));
        when(carRepository.getById(5)).thenReturn(carsMap.get(5));

        List<Car> expectedCars = List.of(carsMap.get(1), carsMap.get(5));

        // Act
        List<Car> cars = carService.getGreenCarsInPriceRange(25000, 45000);

        // Assert
        verify(carRepository, times(1)).findAll();
        assertEquals(expectedCars.size(), cars.size(), "The number of cars should match the expected count.");
        assertTrue(cars.containsAll(expectedCars), "The result list should contain all cars from the expected list.");
    }

    // Test Case 5: Returns car by ID when exists
    @Test
    void shouldReturnCarByIdWhenExists() {
        // Arrange
        int existingCarId = 1;
        Car expectedCar = carsMap.get(existingCarId);
        when(carRepository.getById(existingCarId)).thenReturn(expectedCar);

        // Act
        Car cars = carService.getCarBasicInfoByCarId(existingCarId);

        // Assert
        verify(carRepository, times(1)).getById(existingCarId);
        assertNotNull(cars, "The returned car should not be null.");
        assertEquals(expectedCar, cars, "The returned car should match the expected car.");
    }

    // Test Case 6: Updates an existing car
    @Test
    void shouldUpdateExistingCar() {
        // Arrange
        int carId = 1;
        Car originalCar = carsMap.get(carId);
        Car updatedCar = new Car(carId, "CyberTruck", "Tesla", 2020, FuelType.ELECTRIC, 0.0, 100000);
        when(carRepository.getById(carId)).thenReturn(originalCar);

        // Act
        carService.updateCar(carId, updatedCar);

        // Assert
        verify(carRepository, times(1)).getById(carId);
        verify(carRepository, times(1)).save(originalCar);
    }

    // Test Case 7: Deletes a car by ID
    @Test
    void shouldDeleteCarById() {
        // Arrange
        int carId = 1;

        // Act
        carService.deleteCar(carId);

        // Assert
        verify(carRepository, times(1)).deleteById(carId);
    }

}