package com.senla.ecosystem;

import com.senla.AliveStatus;
import com.senla.animal.*;
import com.senla.environment.Water;
import com.senla.environment.WeatherStatus;
import com.senla.plant.BerryBush;
import com.senla.plant.Grass;
import com.senla.plant.Plant;
import com.senla.plant.Tree;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class EcosystemController {

    private static Long id = 1L;

    private List<Herbivore> herbivores;
    private List<Carnivore> carnivores;
    private List<Plant> plants;
    private Water water;
    private WeatherStatus weatherStatus;
    private Integer turnCounter = 0;

    private Integer lastEventTurn = 0; // Track last event turn
    private static final Integer MIN_EVENT_INTERVAL = 14; // Minimum 2 weeks
    private static final Integer MAX_EVENT_INTERVAL = 4; // Maximum 4 days

    public EcosystemController(int initialPlantAmount, int initialWaterAmount, WeatherStatus weatherStatus) {
        this.herbivores = new ArrayList<>();
        this.carnivores = new ArrayList<>();
        this.plants = new ArrayList<>();
        this.water = new Water(initialWaterAmount);
        this.weatherStatus = weatherStatus;

        plants.add(new Grass(initialPlantAmount));
        plants.add(new BerryBush(initialPlantAmount));
        plants.add(new Tree(initialPlantAmount));

        for (int i = 0; i < 10; i++) {
            herbivores.add(new Rabbit((id++).toString()));
            herbivores.add(new Chipmunk((id++).toString()));
        }
        for (int i = 0; i < 1; i++) {
            carnivores.add(new Wolf((id++).toString()));
            carnivores.add(new Weasel((id++).toString()));
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

    public void automaticSimultaion() throws InterruptedException {
        while (!herbivores.isEmpty() || !carnivores.isEmpty()){
            Thread.sleep(3000);
            simulateTurn();
        }
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
        return (turnCounter - lastEventTurn >= MIN_EVENT_INTERVAL && new Random().nextBoolean());
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

    private void printEcosystemStatus() {
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
}
