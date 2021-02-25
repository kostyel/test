package com.rincentral.test.factories;

import com.rincentral.test.mapper.CarsFullMapperImpl;
import com.rincentral.test.mapper.CarsMapperImpl;
import com.rincentral.test.mapper.CarsMapperInterface;

public abstract class CarsMapperFactory {

    public static CarsMapperInterface create(Boolean isFull) {
        if (isFull) {
            return new CarsFullMapperImpl();
        }
        return new CarsMapperImpl();
    }
}
