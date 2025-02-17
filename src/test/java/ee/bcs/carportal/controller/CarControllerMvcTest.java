package ee.bcs.carportal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

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

}