package com.rincentral.test.services;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalCarsApiService {
    private static final Logger LOGGER = LogManager.getLogger(ExternalCarsApiService.class);

    private static final String URL_ALL_CARS = "http://localhost:8084/api/v1/cars";
    private static final String URL_CAR_BY_ID = "http://localhost:8084/api/v1/cars/%d";
    private static final String URL_ALL_BRANDS = "http://localhost:8084/api/v1/brands";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<ExternalCar> loadAllCars() {
        try {
            ResponseEntity<ExternalCar[]> allCarsResponse = restTemplate
                .getForEntity(URL_ALL_CARS, ExternalCar[].class);
            if (allCarsResponse.getStatusCode() != HttpStatus.OK || allCarsResponse.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(allCarsResponse.getBody());
        } catch (RestClientException restClientException) {
            LOGGER.error("Error when trying to load all cars", restClientException);
            return Collections.emptyList();
        }
    }

    public ExternalCarInfo loadCarInformationById(int id) {
        String carUrl = String.format(URL_CAR_BY_ID, id);
        try {
            ResponseEntity<ExternalCarInfo> carInfoResponse = restTemplate.getForEntity(carUrl, ExternalCarInfo.class);
            if (carInfoResponse.getStatusCode() != HttpStatus.OK || carInfoResponse.getBody() == null) {
                return null;
            }
            return carInfoResponse.getBody();
        } catch (RestClientException restClientException) {
            LOGGER.error("Error when trying to load car with id {}", id, restClientException);
            return null;
        }
    }

    public List<ExternalBrand> loadAllBrands() {
        try {
            ResponseEntity<ExternalBrand[]> allBrandsResponse = restTemplate.getForEntity(URL_ALL_BRANDS, ExternalBrand[].class);
            if (allBrandsResponse.getStatusCode() != HttpStatus.OK || allBrandsResponse.getBody() == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(allBrandsResponse.getBody());
        } catch (RestClientException restClientException) {
            LOGGER.error("Error when trying to load all brands", restClientException);
            return Collections.emptyList();
        }
    }
}
