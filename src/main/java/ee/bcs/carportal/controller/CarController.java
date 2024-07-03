package ee.bcs.carportal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class CarController {


    // Mandatory endpoints go to below
    // Please use @Tag annotation as below with all mandatory endpoints:
    // @Tag(name = "Mandatory")

    @GetMapping("/cars/all")
    @Tag(name = "Mandatory")
    public String getAllCars() {

        return "to be implemented";
    }



    // Bonus endpoints go to below
    // Please use @Tag annotation as below with all bonus endpoints:
    // @Tag(name = "Bonus")



    // Extra practice endpoints go to below
    // Please use @Tag annotation as below with all extra practice endpoints:
    // @Tag(name = "Extra practice")



    // ALL PRIVATE METHODS go to below


}
