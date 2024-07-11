package ee.bcs.carportal.controller;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarController.class)
class CarControllerMvcTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAllModels() throws Exception {
        mockMvc.perform(get("/api/v1/cars/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("All car models: Model 3, Civic, Camry, F-150, Prius; (number of car models: 5)"));
    }

    @Test
    public void shouldReturn3Models() throws Exception {
        mockMvc.perform(get("/api/v1/cars/price-range/from/to")) // Assuming the URL parameters are included in the path
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Cars in price range €28000 - €44000: Model 3, Camry, Prius; (number of car models: 3)"));
    }

    @Test
    public void shouldReturn2GreenModels() throws Exception {
        mockMvc.perform(get("/api/v1/cars/green/price-range/from/to")) // Assuming the URL parameters are included in the path
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Green cars in price range €28000 - €44000: Model 3 (Electric), Prius (Hybrid); (number of car models: 2)"));
    }

    @Test
    public void shouldReturnCorrectTeslaModel3RegistrationTaxRateAndAmount() throws Exception {
        mockMvc.perform(get("/api/v1/car/id/registration-tax")) // Assuming the URL parameters are included in the path
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("The registration tax rate for 2020 Tesla Model 3 is 2.5% with total tax amount €1100"));
    }

    @Test
    public void shouldReturnCorrectTeslaModel3AnnualTaxFee() throws Exception {
        mockMvc.perform(get("/api/v1/car/id/annual-tax")) // Assuming the URL parameters are included in the path
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("The annual tax for 2020 Tesla Model 3 is €50"));
    }

}