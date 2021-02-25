package com.rincentral.test.services;

import com.rincentral.test.exception.BadRequestException;
import com.rincentral.test.exception.NotFoundException;
import com.rincentral.test.mapper.CarsFullMapper;
import com.rincentral.test.mapper.CarsMapper;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.external.NullBrand;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.repository.CarsRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarsRepository carsRepository;
    private final CarsMapper carsMapper;
    private final CarsFullMapper carsFullMapper;
    private List<ExternalCarInfo> externalCarList;
    private List<ExternalBrand> externalBrandList;

    public CarService(CarsRepository carsRepository,
                      CarsMapper carsMapper,
                      CarsFullMapper carsFullMapper) {
        this.carsRepository = carsRepository;
        this.carsMapper = carsMapper;
        this.carsFullMapper = carsFullMapper;
        this.externalCarList = carsRepository.getCarsList();
        this.externalBrandList = carsRepository.getBrandList();
    }

    public List<? extends CarInfo> getCarAll(CarRequestParameters param) {
        List<ExternalCarInfo> externalCarList = filterExternalCar(param);
        List<ExternalBrand> externalBrandList = carsRepository.getBrandList();
        List<CarInfo> carInfoList = new ArrayList<>();
        for (ExternalCarInfo car : externalCarList) {
            ExternalBrand brand;
            CarInfo info = param.getIsFull() ? carsFullMapper.getInfo(car) : carsMapper.getInfo(car);
            brand = externalBrandList.stream().filter(b -> b.getId().equals(car.getBrandId()))
                .findFirst().orElse(new NullBrand());
            info.setBrand(brand.getTitle());
            info.setCountry(brand.getCountry());
            carInfoList.add(info);
        }
        return carInfoList;
    }

    private List<ExternalCarInfo> filterExternalCar(CarRequestParameters param) {
        List<ExternalCarInfo> carInfoList = new ArrayList<>(externalCarList);
        if (param.getSegment() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getSegment().equals(param.getSegment()))
                .collect(Collectors.toList());
        }
        if (param.getSearch() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getModel().contains(param.getSearch())
                    || c.getModification().contains(param.getSearch())
                    || c.getGeneration().contains(param.getSearch()))
                .collect(Collectors.toList());
        }
        if (param.getCountry() != null) {
            List<Integer> brandIdList = externalBrandList.stream()
                .filter(b -> b.getCountry().equals(param.getCountry()))
                .map(ExternalBrand::getId).collect(Collectors.toList());
            carInfoList = carInfoList.stream()
                .filter(c -> brandIdList.contains(c.getBrandId()))
                .collect(Collectors.toList());
        }
        if (param.getMinEngineDisplacement() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getEngineDisplacement() >= param.getMinEngineDisplacement())
                .collect(Collectors.toList());
        }
        if (param.getMinEngineHorsepower() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getHp() >= param.getMinEngineHorsepower())
                .collect(Collectors.toList());
        }
        if (param.getMinMaxSpeed() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getMaxSpeed() >= param.getMinMaxSpeed())
                .collect(Collectors.toList());
        }
        if (param.getYear() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> filterYear(c.getYearsRange(), param.getYear()))
                .collect(Collectors.toList());
        }
        if (param.getBodyStyle() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getBodyStyle().equals(param.getBodyStyle()))
                .collect(Collectors.toList());
        }
        if (param.getModel() != null) {
            carInfoList = carInfoList.stream()
                .filter(c -> c.getModel().equals(param.getModel()))
                .collect(Collectors.toList());
        }
        if (param.getBrand() != null) {
            List<Integer> brandIdList = externalBrandList.stream()
                .map(ExternalBrand::getId)
                .distinct()
                .collect(Collectors.toList());
            carInfoList = carInfoList.stream()
                .filter(c -> brandIdList.contains(c.getBrandId()))
                .collect(Collectors.toList());
        }
        if (carInfoList.isEmpty()) {
            throw new NotFoundException();
        }
        return carInfoList;
    }

    private boolean filterYear(String period, Integer year) {
        String[] creteria = period.split("-");
        if (creteria.length == 2) {
            int periodStart = Integer.parseInt(creteria[0]);
            int periodEnd = creteria[1].equals("present")
                ? LocalDate.now().getYear()
                : Integer.parseInt(creteria[1]);
            return periodStart <= year && year <= periodEnd;
        }
        return false;
    }

    public List<String> getFuelType() {
        return externalCarList.stream()
            .map(ExternalCarInfo::getFuelType)
            .map(FuelType::toString)
            .distinct()
            .collect(Collectors.toList());
    }

    public List<String> getBodyStyle() {
        List<String> bodyStylesList = new ArrayList<>();
        externalCarList.stream()
            .map(car -> {
                String[] bs = car.getBodyStyle().split(", ");
                bodyStylesList.addAll(Arrays.asList(bs));
                return bs;
        }).collect(Collectors.toList());
        return bodyStylesList.stream().map(String::trim).distinct().collect(Collectors.toList());
    }

    public List<String> getEngineType() {
        return externalCarList.stream()
            .map(ExternalCarInfo::getEngineType)
            .map(EngineType::toString)
            .distinct()
            .collect(Collectors.toList());
    }

    public List<String> getWheelDrivesType() {
        return externalCarList.stream()
            .map(ExternalCarInfo::getWheelDriveType)
            .map(WheelDriveType::toString)
            .distinct()
            .collect(Collectors.toList());
    }

    public List<String> getGearboxesType() {
        return externalCarList.stream()
            .map(ExternalCarInfo::getGearboxType)
            .map(GearboxType::toString)
            .distinct()
            .collect(Collectors.toList());
    }

    public Double getMaxSpeedAvg(MaxSpeedRequestParameters param) {

        if ((param.getModel() != null && param.getBrand() != null)
            || (param.getModel() == null && param.getBrand() == null)) {
            throw new BadRequestException();
        }
        List<ExternalCarInfo> carInfoList = new ArrayList<>();

        if (param.getBrand() != null) {
            carInfoList = filterExternalCar(new CarRequestParameters().setBrand(param.getBrand()));
        }
        if (param.getModel() != null) {
            carInfoList = filterExternalCar(new CarRequestParameters().setModel(param.getModel()));
        }

        return carInfoList.stream().map(ExternalCarInfo::getMaxSpeed)
            .reduce(new Averager(),
                Averager::accept,
                Averager::combine)
            .average();

    }


}
