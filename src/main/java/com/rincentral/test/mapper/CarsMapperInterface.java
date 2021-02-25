package com.rincentral.test.mapper;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalCarInfo;

public interface CarsMapperInterface {
    CarInfo getInfo(ExternalCarInfo externalCarInfo);
}
