package ee.bcs.carportal.repository;

import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.persistence.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarRepositoryImplTest {

    @Autowired
    private CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository.resetData();
    }

    // Test cases for save(Car car)

    // Test Case 1: Save a new Car with a unique ID
    @Test
    void shouldSaveNewCarWithUniqueId() {
        // Arrange: Create a new car with a unique ID
        Car newCar = new Car(6, "Leaf", "Nissan", 2021, FuelType.ELECTRIC, 0.0, 32000);

        // Act: Save the new car to the repository and retrieve it
        carRepository.save(newCar);
        Car retrievedCar = carRepository.getById(6);

        // Assert: Verify that the car is saved
        assertNotNull(retrievedCar, "The car should be saved in the repository.");
        assertEquals("Leaf", retrievedCar.getCarModel(), "Car model should be 'Leaf'.");
        assertEquals("Nissan", retrievedCar.getManufacturer(), "Manufacturer should be 'Nissan'.");
        assertEquals(2021, retrievedCar.getModelYear(), "Model year should be 2021.");
        assertEquals(FuelType.ELECTRIC, retrievedCar.getFuelType(), "Fuel type should be ELECTRIC.");
        assertEquals(0.0, retrievedCar.getEmissions(), "Emissions should be 0.0.");
        assertEquals(32000, retrievedCar.getPrice(), "Price should be 32000.");
    }

    // Test Case 2: Save a null Car object (edge case)
    @Test
    void shouldNotSaveNullCar() {
        // Act & Assert: Verify saving a null car throws an exception
        assertThrows(NullPointerException.class, () -> carRepository.save(null), "Saving null car should throw NullPointerException.");
    }

    // Test Case 3: Update a car with an existing ID
    @Test
    void shouldUpdateExistingCar() {
        // Arrange: Create an updated car with an existing ID
        Car updatedCar = new Car(1, "Model S", "Tesla", 2022, FuelType.ELECTRIC, 0.0, 80000);

        // Act: Update the car with a valid existing ID and retrieve the updated car and total count
        carRepository.save(updatedCar);
        Car retrievedCar = carRepository.getById(1);
        int totalCars = carRepository.findAll().size();

        // Verify the car’s details are updated in the repository
        assertNotNull(retrievedCar, "The car should exist in the repository.");
        assertEquals("Model S", retrievedCar.getCarModel(), "Car model should be updated to 'Model S'.");
        assertEquals("Tesla", retrievedCar.getManufacturer(), "Manufacturer should remain 'Tesla'.");
        assertEquals(2022, retrievedCar.getModelYear(), "Model year should be updated to 2022.");
        assertEquals(FuelType.ELECTRIC, retrievedCar.getFuelType(), "Fuel type should be ELECTRIC.");
        assertEquals(0.0, retrievedCar.getEmissions(), "Emissions should be 0.0.");
        assertEquals(80000, retrievedCar.getPrice(), "Price should be updated to 80000.");

        // Ensure no other data is modified
        assertEquals(5, totalCars, "The number of cars should remain the same.");
    }

    // Test cases for getById(int id)

    // Test Case 4: Retrieve a Car by an existing ID
    @Test
    void shouldRetrieveCarByExistingId() {
        // Arrange: Use an existing car ID
        int existingId = 1;

        // Act: Retrieve the car by ID
        Car retrievedCar = carRepository.getById(existingId);

        // Assert: Verify that the car exists in the repository and attributes match expected values
        assertNotNull(retrievedCar, "The car should exist in the repository.");
        assertEquals("Model 3", retrievedCar.getCarModel(), "Car model should be 'Model 3'.");
        assertEquals("Tesla", retrievedCar.getManufacturer(), "Manufacturer should be 'Tesla'.");
    }

    // Test Case 5: Retrieve a Car by an ID that does not exist
    @Test
    void shouldReturnNullWhenRetrievingNonExistentId() {
        // Arrange: Define a non-existent ID
        int nonExistentId = 99;

        // Act: Attempt to retrieve a car with the non-existent ID
        Car retrievedCar = carRepository.getById(nonExistentId);

        // Assert: Verify that the result is null
        assertNull(retrievedCar, "Retrieving a non-existent ID should return null.");
    }

    // Test cases for findAll()

    // Test Case 6: Verify the initial list of cars
    @Test
    void shouldReturnAllInitialCars() {
        // Act: Retrieve all cars
        List<Car> cars = carRepository.findAll();

        // Assert: Verify the size of the list matches the expected count and cars have expected attributes
        assertEquals(5, cars.size(), "The initial number of cars should be 5.");
        assertEquals("Model 3", cars.get(0).getCarModel(), "First car model should be 'Model 3'.");
        assertEquals("Civic", cars.get(1).getCarModel(), "Second car model should be 'Civic'.");
        assertEquals("Camry", cars.get(2).getCarModel(), "Third car model should be 'Camry'.");
        assertEquals("F-150", cars.get(3).getCarModel(), "Fourth car model should be 'F-150'.");
        assertEquals("Prius", cars.get(4).getCarModel(), "Fifth car model should be 'Prius'.");
    }

    // Test Case 7: Verify that calling save() adds a new car to the list
    @Test
    void shouldAddNewCarToList() {
        // Arrange: Create a new car
        Car newCar = new Car(6, "Mustang", "Ford", 2021, FuelType.PETROL, 0.08, 55000);

        // Act: Save the new car and retrieve all cars
        carRepository.save(newCar);
        List<Car> cars = carRepository.findAll();

        // Assert: Verify that the size of the list has increased by one and the new car is in the list
        assertEquals(6, cars.size(), "The number of cars should be 6 after adding a new car.");
        assertTrue(cars.contains(newCar), "The new car should be present in the list.");
    }

    // Test cases for deleteById(int id)

    // Test Case 8: Delete a car by an existing ID
    @Test
    void shouldDeleteCarByExistingId() {
        // Arrange: Use an existing car ID
        int existingId = 1;

        // Act: Delete the cac with the specified ID, retrieve the car, and get total count
        carRepository.deleteById(existingId);
        Car retrievedCar = carRepository.getById(existingId);
        int totalCars = carRepository.findAll().size();

        // Assert: Verify that the car is removed from the repository and size decreases
        assertNull(retrievedCar, "The car should be removed from the repository.");
        assertEquals(4, totalCars, "The number of cars should decrease by one.");
    }

    // Test Case 9: Attempt to delete a non-existent ID (graceful handling)
    @Test
    void shouldGracefullyHandleDeletionOfNonExistentId() {
        // Arrange: Define a non-existent ID
        int nonExistentId = 99;

        // Act & Assert: Deleting a non-existent ID should not throw an exception
        assertDoesNotThrow(() -> carRepository.deleteById(nonExistentId), "Deleting a non-existent ID should not throw an exception.");
        // Act: get total cars count
        int totalCars = carRepository.findAll().size();


        // Verify that the repository state remains unchanged
        assertEquals(5, totalCars, "The repository size should remain unchanged.");
    }
}
