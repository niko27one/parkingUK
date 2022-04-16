package com.parkinguk.parkinguk.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration//initialize java configuration classes ,and it is also considered as spring bean
public class FloorConfigInit {
    @Value("${firstFloorDisable}")//specify what property it is from resources directory
    private int firstFloorDisable;
    @Value("${firstFloorMotorbikes}")
    private int firstFloorMotorbikes;
    @Value("${firstFloorParents}")
    private int firstFloorParents;
}
