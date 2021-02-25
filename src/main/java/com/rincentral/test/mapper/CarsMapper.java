package com.rincentral.test.mapper;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.ExternalCarInfo;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CarsMapper extends CarsMapperInterface {

    @Override
    CarInfo getInfo(ExternalCarInfo externalCarInfo);

}
