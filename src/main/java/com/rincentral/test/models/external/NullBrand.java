package com.rincentral.test.models.external;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NullBrand extends ExternalBrand{

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }
}
