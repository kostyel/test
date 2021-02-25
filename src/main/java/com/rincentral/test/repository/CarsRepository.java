package com.rincentral.test.repository;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.services.ExternalCarsApiService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class CarsRepository {

    private final List<ExternalCarInfo> carsInfoList = new ArrayList<>();
    private final List<ExternalBrand> carsBrandList;

    public CarsRepository(ExternalCarsApiService externalCarsApiService) {
        List<Integer> carsId = externalCarsApiService.loadAllCars()
            .stream().map(ExternalCar::getId).collect(Collectors.toList());
        for(Integer id : carsId){
            carsInfoList.add(externalCarsApiService.loadCarInformationById(id));
        }
        carsBrandList = externalCarsApiService.loadAllBrands();
    }

    public List<ExternalCarInfo> getCarsList () {
        return carsInfoList;
    }

    public List<ExternalBrand> getBrandList () {
        return carsBrandList;
    }
}
