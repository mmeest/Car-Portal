package ee.bcs.carportal.controller;

import jakarta.annotation.Resource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(CarController.class)
class CarControllerMvcTest {

    @Resource
    private MockMvc mockMvc;


}