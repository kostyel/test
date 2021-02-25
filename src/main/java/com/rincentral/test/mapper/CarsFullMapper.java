package com.rincentral.test.mapper;

import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.external.ExternalCarInfo;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CarsFullMapper extends CarsMapperInterface{
    @Override
    @Mapping(source = "bodyLength", target = "bodyCharacteristics.bodyLength")
    @Mapping(source = "bodyWidth", target = "bodyCharacteristics.bodyWidth")
    @Mapping(source = "bodyHeight", target = "bodyCharacteristics.bodyHeight")
    @Mapping(source = "bodyStyle", target = "bodyCharacteristics.bodyStyle")
    @Mapping(source = "fuelType", target = "engineCharacteristics.fuelType")
    @Mapping(source = "engineType", target = "engineCharacteristics.engineType")
    @Mapping(source = "engineDisplacement", target = "engineCharacteristics.engineDisplacement")
    @Mapping(source = "hp", target = "engineCharacteristics.hp")
    CarFullInfo getInfo(ExternalCarInfo externalCarInfo);
}
