package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.ecosystem.EcosystemController;
import com.senla.environment.WeatherStatus;

public class App {
    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        EcosystemController ecoSystem = new EcosystemController(1000, 1000, WeatherStatus.MODERATE);
        ecoSystem.automaticSimultaion();
    }
}
