package com.senla;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.ecosystem.EcosystemController;
import com.senla.environment.WeatherStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest{

    @Test
    public void testSerialization() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        EcosystemController ecoSystem = new EcosystemController(1000, 1000, WeatherStatus.MODERATE);
        String initJsonState = mapper.writeValueAsString(ecoSystem);

        System.out.println(initJsonState);
        EcosystemController restoredController = mapper.readValue(initJsonState, EcosystemController.class);

        for(int i = 0; i < 5; i++){
            ecoSystem.simulateTurn();
            restoredController.simulateTurn();
        }
        Assertions.assertEquals(mapper.writeValueAsString(ecoSystem), mapper.writeValueAsString(restoredController));
    }

}
