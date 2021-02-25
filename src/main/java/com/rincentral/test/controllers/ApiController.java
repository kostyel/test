package com.rincentral.test.controllers;

import static java.util.Collections.emptyList;

import com.rincentral.test.exception.BadRequestException;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.CarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<List<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {
        return ResponseEntity.ok(carService.getCarAll(requestParameters));
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<List<String>> getFuelTypes() {
        return ResponseEntity.ok(carService.getFuelType());
    }

    @GetMapping("/body-styles")
    public ResponseEntity<List<String>> getBodyStyles() {
        return ResponseEntity.ok(carService.getBodyStyle());
    }

    @GetMapping("/engine-types")
    public ResponseEntity<List<String>> getEngineTypes() {
        return ResponseEntity.ok(carService.getEngineType());
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<List<String>> getWheelDrives() {
        return ResponseEntity.ok(carService.getWheelDrivesType());
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<List<String>> getGearboxTypes() {
        return ResponseEntity.ok(carService.getGearboxesType());
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters param) {
        return ResponseEntity.ok(carService.getMaxSpeedAvg(param));
    }
}
