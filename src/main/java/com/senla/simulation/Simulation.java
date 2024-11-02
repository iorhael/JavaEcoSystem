package com.senla.simulation;

import com.senla.AliveStatus;
import com.senla.animal.Carnivore;
import com.senla.animal.Herbivore;
import com.senla.environment.Water;
import com.senla.environment.WeatherStatus;
import com.senla.plant.Plant;
import com.senla.settings.GlobalSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Setter
public class Simulation {

    private static Long nameGen = 1L;

    private Long id;

    private List<Herbivore> herbivores = new ArrayList<>();
    private List<Carnivore> carnivores = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private Water water = new Water();
    private WeatherStatus weatherStatus = WeatherStatus.MODERATE;

    private Integer turnCounter = 0;
    private Integer lastEventTurn = 0; // Track last event turn

    public Simulation(){}

    public Simulation(Long id, int initialPlantAmount, int initialWaterAmount, WeatherStatus weatherStatus) {
        this.water = new Water(initialWaterAmount);
        this.weatherStatus = weatherStatus;
        this.id = id;

        plants.add(new Plant(initialPlantAmount, "Grass"));
        plants.add(new Plant(initialPlantAmount, "BerryBush"));
        plants.add(new Plant(initialPlantAmount, "Tree"));

        for (int i = 0; i < 10; i++) {
            herbivores.add(new Herbivore("rabbit"+(nameGen++), 5));
            herbivores.add(new Herbivore("chipmunk"+(nameGen++), 7));
        }
        for (int i = 0; i < 1; i++) {
            carnivores.add(new Carnivore("wolf"+(nameGen++), 15));
            carnivores.add(new Carnivore("weasel"+(nameGen++), 10));
        }
    }

    public void simulateTurn() {
        turnCounter++;

        // Check for global events
        if (shouldTriggerEvent()) {
            triggerGlobalEvent();
        }

        // Each carnivore tries to eat
        double globalHungerMod = calculateGlobalHungerMod();

        for (Carnivore carnivore : carnivores) {
            carnivore.fulfillNeeds(herbivores, water); // Carnivores try to eat herbivores
            carnivore.nextTurn(globalHungerMod);
        }
        for (Herbivore herbivore : new ArrayList<>(herbivores)) {
            herbivore.fulfillNeeds(plants, water);
            herbivore.nextTurn(globalHungerMod);
        }


        carnivores = evictDeadCarnivores(carnivores);
        herbivores = evictDeadHerbivores(herbivores);


        printEcosystemStatus();
    }

    private double calculateGlobalHungerMod(){
        double mod = 1;
        switch (weatherStatus){
            case COLD: {
                mod = 1.2;
                break;
            }
            case WARM: {
                mod = 0.9;
                break;
            }
            default:{
                break;
            }
        }
        return mod;
    }

    private List<Carnivore> evictDeadCarnivores(List<Carnivore> carnivores){
        return carnivores.stream().filter(a -> {
            if(a.getStatus() == AliveStatus.ALIVE) return true;
            System.out.println("Evicted carnivore dead: " + a.getName());
            return false;
        }).collect(Collectors.toList());
    }
    private List<Herbivore> evictDeadHerbivores(List<Herbivore> herbivores){
        return herbivores.stream().filter(a -> {
            if(a.getStatus() == AliveStatus.ALIVE) return true;
            System.out.println("Evicted herbivore dead: " + a.getName());
            return false;
        }).collect(Collectors.toList());
    }

    private boolean shouldTriggerEvent() {
        return (turnCounter - lastEventTurn >= GlobalSettings.MIN_EVENT_INTERVAL && new Random().nextBoolean());
    }

    private void triggerGlobalEvent() {
        lastEventTurn = turnCounter;
        String event = "";

        switch (new Random().nextInt(3)) {
            case 0:
                water.increaseAmount(200);
                event = "Rain has occurred! Water increased by 200.";
                break;
            case 1:
                weatherStatus = WeatherStatus.COLD;
                event = "The weather has turned cold.";
                break;
            case 2:
                weatherStatus = WeatherStatus.WARM;
                event = "The weather has turned warm.";
                break;
        }
        System.out.println(event);
    }

    public void printEcosystemStatus() {
        System.out.println("\nEcosystem Status:");
        for (Herbivore herbivore : herbivores) {
            System.out.println(herbivore.getName() + " Health: " + herbivore.getHealth());
        }
        for (Carnivore carnivore : carnivores) {
            System.out.println(carnivore.getName() + " Health: " + carnivore.getHealth());
        }
        System.out.println("Water Available: " + water.getAmount());
        System.out.println("Current Temperature: " + weatherStatus);
        System.out.println("------------------------");
    }

    public String simulationToState(){
        StringBuilder sb = new StringBuilder();

        sb.append("Id " + id);
        sb.append(System.lineSeparator());
        for(Herbivore h: herbivores){
            sb.append(h.toSaveFormat());
            sb.append(System.lineSeparator());
        }
        for(Carnivore c: carnivores){
            sb.append(c.toSaveFormat());
            sb.append(System.lineSeparator());
        }
        for(Plant p: plants){
            sb.append(p.toSaveFormat());
            sb.append(System.lineSeparator());
        }
        sb.append(weatherStatus.toSaveFormat());
        sb.append(System.lineSeparator());
        sb.append(water.toSaveFormat());
        sb.append(System.lineSeparator());
        return sb.toString();
    }

}
