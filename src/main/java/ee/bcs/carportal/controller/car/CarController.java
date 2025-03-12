package ee.bcs.carportal.controller.car;

import ee.bcs.carportal.infrastructure.ApiError;
import ee.bcs.carportal.persistence.car.Car;
import ee.bcs.carportal.service.car.CarService;
import ee.bcs.carportal.service.car.dto.CarDetailedInfo;
import ee.bcs.carportal.service.car.dto.CarDto;
import ee.bcs.carportal.service.car.dto.CarInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Car Managament", description = "Operations related to car management")
public class CarController {

    private final CarService carService;

    @PostMapping("/car")
    @Operation(summary = "Adds a new car to the system", description = "Accepts a CarDto object and saves it to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void addCar(@RequestBody @Valid CarDto carDto) {
        carService.addCar(carDto);
    }

    @GetMapping("/car/{carId}")
    @Operation(summary = "Retrieves basic car information", description = "Returns car detaileds for a given carId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car found"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public CarInfo findCarInfo(@PathVariable Integer carId){
        return carService.findCarInfo(carId);
    }

    @GetMapping("/car/detailed-info/{carId}")
    @Operation(summary = "Retrieves detailed car information", description = "Returns comprehensive details of a car using carId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car found"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public CarDetailedInfo findCarDetailedInfo(@PathVariable(name = "carId") Integer carId) {
        return carService.findCarDetailedInfo(carId);
    }

    @GetMapping("/cars/all")
    @Operation(summary = "Fetches a list f all cars", description = "Returns a list of all available cars in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully successfully")
    })
    public List<CarInfo> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/price-range")
    @Operation(summary = "Retrieves cars within a specific price range", description = "Returns a list of cars with prices between from an to")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid price range",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public List<Car> findCarsInPriceRange(@RequestParam Integer from, @RequestParam Integer to) {
        return carService.findCarsInPriceRange(from, to);
    }

    @GetMapping("/cars/price-range-fueltype")
    @Operation(summary = "Regrieves cars based on price and fuel type", description = "Returns a list of cars within a price range that match the specific fuel type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public List<Car> findCarsInPriceRangeWithFuelType(@RequestParam  Integer from, @RequestParam Integer to, @RequestParam String fuelTypeCode ) {
        return carService.findCarsInPriceRangeWithFuelType(from, to, fuelTypeCode);
    }

    @PutMapping("/car/{carId}")
    @Operation(summary = "Updates car details", description = "Modifies an existing car record identified by carId with new data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car updated successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void updateCar(@PathVariable Integer carId, @RequestBody @Valid CarDto carDto){
        carService.updateCar(carId, carDto);
    }

    @DeleteMapping("/car/{carId}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)      // Added for response code 204
    @Operation(summary = "Removes a car from the system", description = "Deletes the car entry associated with the given carId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public void deleteCar(@PathVariable Integer carId){
        carService.deleteCar(carId);
    }
}
