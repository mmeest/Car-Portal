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
                .andExpect(content().string("All car models: Model 3, Civic, Camry, F-150, Prius;"));
    }

    @Test
    public void shouldReturn28000To44000() throws Exception {
        mockMvc.perform(get("/api/v1/cars/price-range/from/to")) // Assuming the URL parameters are included in the path
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("from €28000 to €44000"));
    }

}