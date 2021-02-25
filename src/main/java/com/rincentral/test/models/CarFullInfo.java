package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarFullInfo extends CarInfo {

    @JsonProperty("year_range")
    private String yearsRange;

    @JsonProperty("gearbox")
    private GearboxType gearboxType;

    @JsonProperty("wheel_drive")
    private WheelDriveType wheelDriveType;

    @JsonProperty("body_characteristics")
    private BodyCharacteristics bodyCharacteristics;

    @JsonProperty("engine_characteristics")
    private EngineCharacteristics engineCharacteristics;

    @JsonProperty("acceleration")
    private Double acceleration;

    @JsonProperty("max_speed")
    private Integer maxSpeed;

}
