package ee.bcs.carportal.controller.car;

import ee.bcs.carportal.service.car.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // ↓↓↓ Endpoints will go below here ↓↓↓

}
