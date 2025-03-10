package ee.bcs.carportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.bcs.carportal.service.car.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    // Add Car Test
    @Test
    public void shouldAddCar() throws Exception {
        CarDto newCar = new CarDto( 1, 1, "Model S", 2020, new BigDecimal("0.00"), 55000);
        String json = new ObjectMapper().writeValueAsString(newCar);

        mockMvc.perform(post("/api/v1/car")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    // Find Car Info Test
    @Test
    public void shouldReturnCarInfo() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_car_info.json").toURI());
        String expectedJson = Files.readString(path);

        mockMvc.perform(get("/api/v1/car/{carId}", 1)) // Replace 1 with a valid car ID
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    // Find Car Detailed Info Test
    @Test
    public void shouldReturnCarDetailedInfo() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_car_detailed_info.json").toURI());
        String expectedJson = Files.readString(path);

        mockMvc.perform(get("/api/v1/car/detailed-info/{carId}", 1)) // Replace 1 with a valid car ID
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnAllCars() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_all_cars.json").toURI());
        String expectedJson = Files.readString(path);

        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnCarsInPriceRange() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_cars_price_range_30000_to_45000.json").toURI());
        String expectedJson = Files.readString(path);

        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/price-range")
                        .param("from", "30000")
                        .param("to", "45000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldNotReturnAnyCarsInPriceRange() throws Exception {
        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/price-range")
                        .param("from", "500")
                        .param("to", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    @Test
    public void shouldReturnCarsInPriceRange20000To25000WithFuelTypePetrol() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_cars_price_range_with_fuel_type_20000_to_25000_P.json").toURI());
        String expectedJson = Files.readString(path);

        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/price-range-fueltype")
                        .param("from", "20000")
                        .param("to", "25000")
                        .param("fuelTypeCode", "P")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnCarsInPriceRange20000To30000WithFuelTypePetrol() throws Exception {
        // Load the expected JSON from the file
        Path path = Paths.get(ResourceUtils.getFile("classpath:files/expected_cars_price_range_with_fuel_type_20000_to_30000_P.json").toURI());
        String expectedJson = Files.readString(path);

        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/price-range-fueltype")
                        .param("from", "20000")
                        .param("to", "30000")
                        .param("fuelTypeCode", "P")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldNotReturnAnyCarsInPriceRangeWithFuelType() throws Exception {
        // Assert: Perform the GET request and check the response
        mockMvc.perform(get("/api/v1/cars/price-range-fueltype")
                        .param("from", "20000")
                        .param("to", "25000")
                        .param("fuelTypeCode", "H"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    // Update Car Test
    @Test
    public void shouldUpdateCar() throws Exception {
        CarDto updatedCar = new CarDto(3, 2, "Model 3", 2020,new BigDecimal("5.55"),3000);
        String json = new ObjectMapper().writeValueAsString(updatedCar);

        mockMvc.perform(put("/api/v1/car/{carId}", 1) // Replace ID with a valid car ID
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    // Delete Car Test
    @Test
    public void shouldDeleteCar() throws Exception {
        mockMvc.perform(delete("/api/v1/car/{carId}", 5)) // Replace ID with a valid car ID
                .andExpect(status().isOk());
    }

}