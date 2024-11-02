package com.senla.utils;

import com.senla.AliveStatus;
import com.senla.animal.Animal;
import com.senla.animal.Carnivore;
import com.senla.animal.Herbivore;
import com.senla.environment.Water;
import com.senla.environment.WeatherStatus;
import com.senla.plant.Plant;
import com.senla.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationBuilder {
    private final static String whiteSpaceSplit = "\\s+";

    private List<Simulation> simulations = new ArrayList<>();
    private Simulation currentBuild = null;

    public SimulationBuilder(){}

    public List<Simulation> getBuiltSimulations(){
        return simulations;
    }

    public void readSimulations(String line){
        if(line.isBlank()) return;
        if(line.startsWith("-")){
            if(currentBuild != null) simulations.add(currentBuild);
            currentBuild = new Simulation();
            return;
        }
        parseLine(currentBuild, line);
    }

    private void parseLine(Simulation simulation, String line){
        String[] tokenArray = line.split(whiteSpaceSplit);
        switch (tokenArray[0]){
            case "Id": {
                simulation.setId(Long.valueOf(tokenArray[1]));
                break;
            }
            case "herbivore": {
                Herbivore h = AnimalBuilder.herbivoreBuilder(tokenArray);
                simulation.getHerbivores().add(h);
                break;
            }
            case "carnivore": {
                Carnivore c = AnimalBuilder.carnivoreBuilder(tokenArray);
                simulation.getCarnivores().add(c);
                break;
            }
            case "plant": {
                Plant p = new Plant();
                p.setType(tokenArray[1]);
                p.setAmount(Integer.valueOf(tokenArray[2]));
                simulation.getPlants().add(p);
                break;
            }
            case "weather": {
                WeatherStatus w = WeatherStatus.valueOf(tokenArray[1]);
                simulation.setWeatherStatus(w);
                break;
            }
            case "water": {
                Water w = new Water();
                w.setAmount(Integer.valueOf(tokenArray[1]));
                simulation.setWater(w);
                break;
            }
            default:{
                break;
            }
        }
    }
}
